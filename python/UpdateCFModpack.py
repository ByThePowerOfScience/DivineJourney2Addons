import glob
import os
import shutil

mods_path = "/Users/caitlynbrandt/Documents/curseforge/minecraft/Instances/Divine Journey 2 (1)/mods/"

def main():
	try:
		os.remove(glob.glob(mods_path + "dj2addons-*.jar")[0])
	except FileNotFoundError and IndexError:
		pass
	
	
	shutil.copyfile(glob.glob("./build/libs/dj2addons-*.jar")[0], mods_path + "/dj2addons.jar")
	try:
		shutil.copyfile("./Test.zs", mods_path + "../scripts/Test.zs")
	except FileNotFoundError:
		pass

main()