from __future__ import unicode_literals
from __future__ import print_function

import json
from StringIO import StringIO

def get_hadoop_settings(items=None):
    try:
        with open('app/settings.json') as file:
            data = json.load(file)
            if items is None:
                return data["hadoop"]
            hadoop = dict()
            for item in items:
                hadoop[item] = data["hadoop"][item]
            return hadoop
    except IOError as e:
        print( e )
