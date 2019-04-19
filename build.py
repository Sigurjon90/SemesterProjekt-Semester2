import os
from collections import OrderedDict
import subprocess

root = os.getcwd()

def log(out, dir):
  if "[INFO] BUILD SUCCESS\n[INFO]" in str(out, "UTF-8"):
    print("SUCCESS for %s " % dir)
  else:
    with open("log.log", "wb") as f:
      f.write(out)
    print("FAILED for %s see %s" % (dir, dir + "/log.log"))

def run(dir):
  os.chdir(dir)
  process = subprocess.Popen(
    "mvn clean package exec:java",
    shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE
  )
  out, err = process.communicate()
  log(out, dir)
  errcode = process.returncode
  return (out, err, errcode)

def get_items(dir):
  sorted_list = sorted(
    filter(
      lambda x: not os.path.isfile(os.path.join(dir, x)) and not x.startswith("."), [i for i in os.listdir(dir)]
    )
  )
  sorted_list = [os.path.join(root, i) for i in sorted_list]
  return OrderedDict(zip(range(0, len(sorted_list)), sorted_list))

if __name__ == "__main__":
  display_items = get_items(root)
  #print options
  list(map(print, ["%s   %s" % (k, v) for k, v in display_items.items()]))
  #ask for projects to compile
  input_ = eval(input("choose projects coma separated:\n "))
  #build projects!
  list(map(run, [display_items[i] for i in input_]))