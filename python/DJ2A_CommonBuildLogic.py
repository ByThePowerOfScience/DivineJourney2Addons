import glob
import re
from pathlib import Path
from typing import AnyStr, Pattern, Union, Any

from packaging import version

def get_coremod_dir(projectDir: str):
	return Path(projectDir) / "coremod"

def get_mod_dir(projectDir: str):
	return Path(projectDir) / "mod"

out_jar_subdir = "build/libs"

def coremod_path(projectDir: str):
	return get_coremod_dir(projectDir) / out_jar_subdir / "dj2addonscore-*"
def mod_path(projectDir: str):
	return get_mod_dir(projectDir) / out_jar_subdir / "dj2addons-*"


mod_version_regex = re.compile(r"dj2addons-([0-9.]+(?:-\w+)?)\.jar")
coremod_version_regex = re.compile(r"dj2addonscore-([0-9.]+(?:-\w+)?)\.jar")




def getLatestVersion(s: Any, version_regex: Pattern[AnyStr]) -> Path:
	if type(s) is not str:
		s = str(s)
	
	possibles = [el for el in glob.glob(s) if 'sources' not in el]
	
	if len(possibles) == 0:
		raise RuntimeError("No possible mod jars found at " + s)
	elif len(possibles) == 1:
		return Path(possibles[0])
	
	latestVersion = version.parse("0.0.0")
	latestJarPath = None
	for jarPath in possibles:
		jarName = jarPath.split("/")[-1]
		
		re_jarVersionMatch = version_regex.match(jarName)
		if re_jarVersionMatch is not None:
			jarVersion = re_jarVersionMatch.groups()[0]
			if (version.parse(jarVersion) > latestVersion):
				latestJarPath = jarPath
		else:
			print(f'invalid version for {jarName}')
	
	if latestJarPath is not None:
		return Path(latestJarPath)
	
	raise RuntimeError('No valid jar found in set of ' + str([possibles]))


def getLatestModBuild(projectDir: str) -> Path:
	return getLatestVersion(mod_path(projectDir), mod_version_regex)


def getLatestCoremodBuild(projectDir: str) -> Path:
	return getLatestVersion(coremod_path(projectDir), coremod_version_regex)
