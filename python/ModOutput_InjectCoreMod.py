import glob
import re
import zipfile
from pathlib import Path

from packaging import version

versionRegex = re.compile(r"dj2addons-([0-9.]+(?:-\w+)?)\.jar")


def getModPath(s):
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


def main():
	modjar = getModPath(str(mod_path))
	with zipfile.ZipFile(modjar, mode='a') as file:
		file.write(coremod_path, '/META-INF/libraries/dj2addons-core.jar')


main()
