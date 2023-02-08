import glob
import os
import re
import shutil
from packaging import version

mods_path = os.path.expanduser('~') + "/Documents/curseforge/minecraft/Instances/DJ2 Addons Test/mods/"
buildpath = "../build/libs/"

def findnewestjar(modjars):
	jarname = ''
	for path in modjars:
		if isinstance(path, str):
			filename = re.split('[/\\\\]', path)[-1]
			splitname = filename.split('-')
			if (splitname[0] == 'dj2addons') and (not 'sources' in filename):
				if not jarname:
					jarname = filename
				else:
					current = version.parse(jarname.removesuffix('.jar'))
					thisone = version.parse(splitname[1].removesuffix('.jar'))
					if thisone > current:
						jarname = filename
	if not jarname:
		print('No valid jars found? Found the following:')
		for path in modjars:
			print(path)
		return
	else:
		return jarname

def main():
	for file in glob.glob(mods_path + "dj2addons*.jar"):
		try:
			print('deleting ' + file)
			#os.remove(file)
		except FileNotFoundError or IndexError:
			print('Failed to find any file to delete.')
			pass
	
	modjars = glob.glob(buildpath + 'dj2addons-*.jar')
	
	jarname = findnewestjar(modjars)
	
	print(f'copying {jarname} to "{mods_path.split("/")[-3]}" mods folder')
	shutil.copyfile(buildpath + jarname, mods_path + jarname)
	try:
		shutil.copyfile("./Test.zs", mods_path + "../scripts/Test.zs")
	except FileNotFoundError:
		pass

main()


