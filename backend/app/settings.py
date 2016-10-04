from __future__ import unicode_literals
from __future__ import print_function

import json
from StringIO import StringIO

def get_hadoop_settings(items=None):
    data = hadoop_settings
    if items is None:
        return data
    hadoop = dict()
    for item in items:
        hadoop[item] = data[item]
    return hadoop

hadoop_settings = {
"master" : "localhost" ,
"paths": {"hadoop_home" : "/usr/local/hadoop" , "bin" : "/bin" , "sbin" : "/sbin"} ,
"slaves" : ["localhost"],
"actions": {"start": "start-all.sh", "stop": "stop-all.sh", "restart": "restart", "upload": "fs -put", "run": "jar"}
}
