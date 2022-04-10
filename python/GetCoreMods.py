import re
import subprocess
import zipfile
import fileinput

my_core_mods = ["org.btpos.dj2addons.bootstrapper.core.DJ2ALoadingPlugin"]

def getDepsFromBuildScript():
	bpattern = re.compile("(?:implementation|runtimeOnly)(?:\\(\\s?(?:[\t ]*[\\w.:\"'\\-(),]+\\n?)+)")
	deps = []
	with open("../build.gradle") as file:
		buildscript = file.read()
		blocks = re.findall(bpattern, buildscript)
		dep_pattern = re.compile(".*['\"]([\\w:.\\-\\n]+)[\"']")
		
		for b in blocks:
			s = re.finditer(dep_pattern, b)
			for s1 in s:
				deps.append(str(s1.group(1)))
	return deps

def convertDepsToFolderPaths(deps):
	out = []
	for d in deps:
		d1 = d.split(":")
		path = ""
		for s in d1:
			path += "/" + s.replace(".", "/")
		out.append(path)
	return out

cache_dir = "~/.gradle/caches/forge_gradle/deobf_dependencies"
def getJarPaths(paths):
	files = []
	for p in paths:
		with subprocess.Popen("cd %s*;echo $PWD" % (cache_dir + p), shell=True,stdout=subprocess.PIPE) as process:
			out, err = process.communicate()
			s = str(out).replace("'", "").replace("\\n", "").lstrip("abcdef1234567890")
			sarr = s.split("/")
			files.append(s + "/" + sarr[len(sarr)-2] + "-" + sarr[len(sarr)-1] + ".jar")
	return files


def getCoreMods(files):
	coremods = []
	for f in files:
		manifest = str(zipfile.ZipFile(f, "r") \
					   .open("META-INF/MANIFEST.MF") \
					   .read()).split("\\r\\n")
		for line in manifest:
			if "FMLCorePlugin:" in line:
				coremods.append(line.split(" ")[1])
	return coremods

coremods_prop = "coremods="
def setCoreMods(s):
	with open("../gradle.properties", "r") as file:
		data = file.readlines()
	
	for i in range(len(data)):
		if (coremods_prop in data[i]):
			data[i] = coremods_prop + s + (' ' * (len(data[i]) - (len(s) + len(coremods_prop)))) + '\n'
	
	with open("../gradle.properties", "w") as file:
		file.writelines(data)



def main():
	deps = getDepsFromBuildScript()
	
	paths = convertDepsToFolderPaths(deps)
	
	files = getJarPaths(paths)
	
	coremods = getCoreMods(files)
	
	for m in my_core_mods:
		coremods.append(m)
	
	setCoreMods(','.join(coremods))
	
	
	
	
	
	
main()
	
	




