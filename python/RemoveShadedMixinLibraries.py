import os
import subprocess
from pathlib import Path

deobf_path = Path("~/.gradle/caches/minecraft/deobfedDeps/deobf/").expanduser()
offenders = [
	"curse/maven/Bewitchment-285439/3256343/Bewitchment-285439-3256343.jar"
]
lib_path = "org/spongepowered/*"

cmd_template = ["zip", "-d", lib_path]


def main():
	for jar_path in offenders:
		cmd = cmd_template.copy()
		cmd.insert(2, deobf_path / jar_path)
		try:
			subprocess.call(cmd)
		except FileNotFoundError as e:
			print(cmd)
			print(f"filename: {e.filename}")


main()
