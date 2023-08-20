import glob
import os
import zipfile

def getModPath(s):
	possibles = glob.glob(os.path.expanduser(s))
	for p in possibles:
		if 'sources' in p:
			continue
		return p
	raise RuntimeError('No mod jar found.')


coremod_path = os.path.expanduser("~/IdeaProjects/DJ2Addons/coremod/build/libs/dj2addons-core.jar")
mod_path = getModPath("~/IdeaProjects/DJ2Addons/mod/build/libs/dj2addons-*.jar")

def main():
	with zipfile.ZipFile(mod_path, 'a') as file:
		file.write(coremod_path, '/META-INF/libraries/dj2addons-core.jar')

main()
