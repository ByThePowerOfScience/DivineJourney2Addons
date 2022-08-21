import os
import re
import zipfile
import glob

from typing import List


def getDepsFromBuildScript():
	bpattern = re.compile("(?:deobfCompile|runtimeOnly)\\(\\s?(?:[\t ]*[\\w.:\"'\\-(),]+\\n?)+")
	deps = []
	with open("./build.gradle") as file:
		buildscript = file.read()
		blocks = re.findall(bpattern, buildscript)
		dep_pattern = re.compile(".*['\"]([\\w:.\\-\\n]+)[\"']")
		
		for b in blocks:
			s = re.finditer(dep_pattern, b)
			for s1 in s:
				deps.append(str(s1.group(1)))
	return deps



curse_pattern = re.compile("curse.maven:(\\w+)-([0-9]+):([0-9]+)")
def getCurseForgeDeps(l: List[str]):
	out = []
	for s in l:
		if "curse.maven" not in s:
			continue
		t = curse_pattern.findall(s)
		out.append(t[0])
	return out


manifest_json = """{
  "minecraft": {
    "version": "1.12.2",
    "modLoaders": [
      {
        "id": "forge-14.23.5.2860",
        "primary": true
      }
    ]
  },
  "manifestType": "minecraftModpack",
  "manifestVersion": 1,
  "name": "DJ2 Addons Test",
  "version": "0.0",
  "author": "ABadHaiku",
  "files": [
    %s
  ]
}
"""

files_template = """{
      "projectID": %s,
      "fileID": %s,
      "required": true
    }"""
def generateModpackZip(tuples):
	generateManifestJson(tuples)
	try:
		os.remove("./DJ2AddonsTest.zip")
		# os.remove("/Applications/MultiMC.app/Data/instances/DJ2AddonsTest")
	except FileNotFoundError or PermissionError:
		pass
	
	# try:
	# 	os.remove("./out/temp/Test.zs")
	# except FileNotFoundError:
	# 	pass
	#
	# with open("./out/temp/Test.zs", "x") as file:
	# 	file.write("import mods.dj2addons.*;")
	#
	with zipfile.ZipFile("./DJ2AddonsTest.zip", 'w') as file:
		file.write("./temp/manifest.json", "manifest.json")
		file.write(glob.glob("./build/libs/dj2addons-*.jar")[1], "/overrides/mods/dj2addons.jar")
		file.write("./Test.zs", "/overrides/scripts/Test.zs")



def generateManifestJson(tuples):
	try:
		os.remove("./temp/manifest.json")
	except FileNotFoundError:
		pass
	
	with open("./temp/manifest.json", "x") as file:
		files = []
		for t in tuples:
			files.append(files_template % (t[1], t[2]))
		file.write(manifest_json % ',\n'.join(files))
	


def main():
	deps = getDepsFromBuildScript()
	tuples = getCurseForgeDeps(deps)
	generateModpackZip(tuples)
	

main()






