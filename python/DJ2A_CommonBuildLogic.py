import glob
import re
from pathlib import Path
from typing import AnyStr, Pattern, Union

from packaging import version

project_dir = Path("~/IdeaProjects/DJ2Addons/").expanduser()
coremod_dir = project_dir / "coremod"
mod_dir = project_dir / "mod"

out_jar_subdir = "build/libs"

coremod_path = coremod_dir / out_jar_subdir / "dj2addonscore-*.jar"
mod_jar_path = mod_dir / out_jar_subdir / "dj2addons-*.jar"


mod_version_regex = re.compile(r"dj2addons-([0-9.]+(?:-\w+)?)\.jar")
coremod_version_regex = re.compile(r"dj2addonscore-([0-9.]+(?:-\w+)?)\.jar")





def getLatestVersion(s: Union[str, Path], version_regex: Pattern[AnyStr]) -> Path:
	if (type(s) is str):
		possibles = glob.glob(s)
	elif (type(s) is Path):
		possibles = s.glob(s.name)
	else:
		raise RuntimeError(f"Invalid type {type(s)} given, accepted types are 'str' or 'pathlib.Path'")
	
	if len(possibles) == 0:
		raise RuntimeError("No possible mod jars found at " + s)
	
	latestVersion = version.parse("0.0.0")
	latestJarPath = None
	
	for jarPath in possibles:
		jarName = jarPath.split("/")[-1]
		if 'sources' in jarName:
			continue
		re_jarVersionMatch = version_regex.match(jarName)
		if re_jarVersionMatch is not None:
			jarVersion = re_jarVersionMatch.groups()[0]
			if (version.parse(jarVersion) > latestVersion):
				latestJarPath = jarPath
	
	if latestJarPath is not None:
		return Path(latestJarPath)
	
	raise RuntimeError('No valid jar found in set of ' + str([possibles]))

def getLatestModBuild() -> Path:
	return getLatestVersion(str(mod_jar_path), mod_version_regex)

def getLatestCoremodBuild() -> Path:
	return getLatestVersion(str(coremod_path), coremod_version_regex)
