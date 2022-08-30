import os
import subprocess

deobf_path = os.path.expanduser("~/.gradle/caches/minecraft/deobfedDeps/deobf/")
offenders = [
	"curse/maven/Bewitchment-285439/3256343/Bewitchment-285439-3256343.jar"
]
lib_path = "org/spongepowered/*"

cmd_template = ["zip", "-d"]

def main():
	for jar_path in offenders:
		cmd = cmd_template.copy()
		cmd.append(deobf_path + jar_path)
		cmd.append(lib_path)
		subprocess.call(cmd)

			

main()