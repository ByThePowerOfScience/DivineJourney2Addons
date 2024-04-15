import glob
import re
import zipfile
from pathlib import Path

from packaging import version

versionRegex = re.compile(r"dj2addons-([0-9.]+(?:-\w+)?)\.jar")

def getLatestModJar(s):
	possibles = glob.glob(s)
	if len(possibles) == 0:
		raise RuntimeError("No possible mod jars found at " + s)
	
	latestVersion = version.parse("0.0.0")
	latestJarPath = None
	
	for jarPath in possibles:
		jarName = jarPath.split("/")[-1]
		if 'sources' in jarName:
			continue
		re_jarVersionMatch = versionRegex.match(jarName)
		if re_jarVersionMatch is not None:
			jarVersion = re_jarVersionMatch.groups()[0]
			if (version.parse(jarVersion) > latestVersion):
				latestJarPath = jarPath
	
	if latestJarPath is not None:
		return latestJarPath
	raise RuntimeError('No mod jar found at ' + str([possibles]))


project_dir = Path("~/IdeaProjects/DJ2Addons/").expanduser()
coremod_path = project_dir / "coremod/build/libs/dj2addons-core.jar"
mod_path = project_dir / "mod/build/libs/dj2addons-*.jar"

mixinextras = Path("~/.gradle/caches/modules-2/files-2.1/io.github.llamalad7/mixinextras-common/0.3.5/5de895137aa0478675b6ecf7602d9652b05dc37e/mixinextras-common-0.3.5.jar").expanduser()

def jankShadowMixinExtrasIntoCoremod():
	with zipfile.ZipFile(mixinextras, mode='r') as mixExtJar:
		with zipfile.ZipFile(coremod_path, mode='a') as coremodJar:
			for name in mixExtJar.namelist():
				if 'com/llamalad7' not in name:
					continue
				coremodJar.writestr(name, mixExtJar.read(name))

def jankShadowCoremodIntoMod():
	modjar = getLatestModJar(str(mod_path))
	print("Copying coremod to " + modjar)
	with zipfile.ZipFile(modjar, mode='a') as file:
		file.write(coremod_path, '/META-INF/libraries/dj2addons-core.jar')
		# file.write(mixinextras, '/META-INF/libraries/_mixinextras-common-0.3.5.jar')  # only way I could find to rename it when copying

def main():
	# jankShadowMixinExtrasIntoCoremod()
	jankShadowCoremodIntoMod()


main()
