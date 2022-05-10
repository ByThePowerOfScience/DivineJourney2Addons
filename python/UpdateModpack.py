import glob
import os
import shutil

mods_path = "/Applications/MultiMC.app/Data/instances/DJ2AddonsTest/minecraft/mods/"

def main():
	try:
		os.remove(glob.glob(mods_path + "dj2addons-*.jar")[0])
	except FileNotFoundError and IndexError:
		pass
	
	
	shutil.copyfile(glob.glob("./build/libs/dj2addons-*.jar")[0], mods_path + "/dj2addons.jar")
	shutil.copyfile("./Test.zs", mods_path + "../scripts/Test.zs")

main()