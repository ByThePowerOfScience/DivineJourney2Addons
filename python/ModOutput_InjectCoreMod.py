import glob
import re
import zipfile
from pathlib import Path

from packaging import version

version_regex = re.compile(r"/dj2addons-([0-9.]+).*\.jar/")


def getModPath(s):
    possibles = glob.glob(s)
    if len(possibles) == 0:
        raise RuntimeError("No possible mod jars found at " + s)
    latest = version.parse("0.0.0")
    latestjar = None
    for jarName in possibles:
        if 'sources' in jarName:
            continue
        jarVersion = version_regex.match(jarName).groups()
        if jarVersion is None:
            print("No jar version found for " + jarName)
            continue
        else:
            if jarVersion[0] > latest:
                latestjar = jarName
    
    if latestjar is not None:
        return latestjar
    else:
        raise RuntimeError('No mod jar found at ' + str([path for [path, _jar] in possibles]))


project_dir = Path("~/IdeaProjects/DJ2Addons/").expanduser()
coremod_path = project_dir / "coremod/build/libs/dj2addons-core.jar"
mod_path = project_dir / "mod/build/libs/dj2addons-*.jar"


def main():
    modjar = getModPath(str(mod_path))
    with zipfile.ZipFile(modjar, 'a') as file:
        file.write(coremod_path, '/META-INF/libraries/dj2addons-core.jar')


main()
