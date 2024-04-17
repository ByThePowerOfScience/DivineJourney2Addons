import glob
import os
import shutil
from DJ2A_CommonBuildLogic import getLatestModBuild

modpack_path = os.path.expanduser('~') + "/Documents/curseforge/minecraft/Instances/DJ2 Addons Test/mods/"

def main():
	for file in (glob.glob(modpack_path + "dj2addons*.jar") + glob.glob(modpack_path + "/1.12.2/*.jar")):
		try:
			print('deleting ' + file)
			os.remove(file)
		except FileNotFoundError or IndexError:
			print('No mod file to delete found in mods dir.')
			pass
	
	jar_path = getLatestModBuild()
	
	assert jar_path is not None, "No valid jar found from build directory."
	
	print(f'copying {jar_path} to "{modpack_path.split("/")[-3]}" mods folder')
	shutil.copyfile(jar_path, modpack_path + jar_path.name)
	
	try:
		shutil.copyfile("./Test.zs", modpack_path + "../scripts/Test.zs")
	except FileNotFoundError:
		pass


main()
