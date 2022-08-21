import glob
import os
import shutil

mods_path = "/Users/caitlynbrandt/Documents/curseforge/minecraft/Instances/DJ2 Addons Test/mods/"

def main():
	try:
		os.remove(mods_path + "dj2addons.jar")
	except FileNotFoundError or IndexError:
		pass
	
	
	shutil.copyfile(glob.glob("./build/libs/dj2addons-*.jar")[1], mods_path + "dj2addons.jar")
	try:
		shutil.copyfile("./Test.zs", mods_path + "../scripts/Test.zs")
	except FileNotFoundError:
		pass

main()