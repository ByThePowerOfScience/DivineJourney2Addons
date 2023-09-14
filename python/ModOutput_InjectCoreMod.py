import glob
import os
import zipfile
from pathlib import Path
from packaging import version
import re

# x = re.compile("/dj2addons-([0-9.]+)\\.jar/")


def getModPath(s):
    possibles = glob.glob(s)
    if len(possibles) == 0:
        raise RuntimeError("No possible mod jars found at " + s)
    # latest = version.parse("0.0.0")
    # latestjar = None
    for x in possibles:
        if 'sources' in x:
            continue
        return x
    raise RuntimeError('No mod jar found at ' + str([path for [path, _jar] in possibles]))


project_dir = Path("~/IdeaProjects/DJ2Addons/").expanduser()
coremod_path = project_dir / "coremod/build/libs/dj2addons-core.jar"
mod_path = project_dir / "mod/build/libs/dj2addons-*.jar"


def main():
    modjar = getModPath(str(mod_path))
    with zipfile.ZipFile(modjar, 'a') as file:
        file.write(coremod_path, '/META-INF/libraries/dj2addons-core.jar')


main()
