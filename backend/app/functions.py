import subprocess

services = ['NodeManager', 'DataNode', 'NameNode', 'ResourceManager', 'SecondaryNameNode']


def run_command(command=None, return_out=False):
    process = subprocess.Popen(command.split(), stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    if return_out:
        return process.communicate()[0]
    print process.communicate()[0]


def check_hadoop_services():
    output = run_command("jps", True)
    all_running = True
    for service in services:
        if service not in output:
            all_running = False
    return all_running
