import zipfile

from DJ2A_CommonBuildLogic import getLatestModBuild, getLatestCoremodBuild


# mixinextras = Path("~/.gradle/caches/modules-2/files-2.1/io.github.llamalad7/mixinextras-common/0.3.5/5de895137aa0478675b6ecf7602d9652b05dc37e/mixinextras-common-0.3.5.jar").expanduser()
# def jankShadowMixinExtrasIntoCoremod():
# 	with zipfile.ZipFile(mixinextras, mode='r') as mixExtJar:
# 		with zipfile.ZipFile(coremod_path, mode='a') as coremodJar:
# 			for name in mixExtJar.namelist():
# 				if 'com/llamalad7' not in name:
# 					continue
# 				coremodJar.writestr(name, mixExtJar.read(name))

def jankShadowCoremodIntoMod():
	mod_jar = getLatestModBuild()
	coremod_jar = getLatestCoremodBuild()
	print(f"Copying {coremod_jar} to {mod_jar}")
	with zipfile.ZipFile(mod_jar, mode='a') as file:
		file.write(coremod_jar, f'/META-INF/libraries/{coremod_jar.name}')  # keep same name
		# file.write(mixinextras, '/META-INF/libraries/_mixinextras-common-0.3.5.jar')  # only way I could find to rename it when copying

def main():
	# jankShadowMixinExtrasIntoCoremod()
	jankShadowCoremodIntoMod()


main()
