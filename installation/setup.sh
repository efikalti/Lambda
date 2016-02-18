#!/bin/bash


# Function that prints to stderr with red colour
echoerr() {
  echo -e "[ERROR] \e[01;31m$1\e[0m" >&2
}


# Function that prints warnings with yellow colour
echowarn() {
  echo -e "[WARN] \e[01;33m$1\e[0m"
}


# Function that prints information with green colour
echoinfo(){
  echo -e "[INFO] \e[32m$1 $2 $3\e[0m"
}


# Function to check if Git is present in the system and install it if not.
git_check() {

  # Check if git is installed in the system
  command -v git >/dev/null 2>&1 ||
  {
    # Try to install git if system is Ubuntu or Centos
    echowarn  "Git is required but it's not installed.";
    echoinfo  "Installing Git...";
    if [ "$1" = "Ubuntu" ];
      then
        echoinfo "ubuntu"
      else if [ "$1" = "Centos" ];
        then
          echoinfo "centos"
      fi
    fi
    exit 1;
  }

  # Print git installed version
  command git --version 2>&1 | (head -n1 && tail -n1) | awk '{split($0, a, " "); print "[INFO] Git version installed", a[3]}'
}


# MAIN
echoinfo  "Setup starting...";
echo

# Get the system specifications
echoinfo  "System specifications:";

OS=$(lsb_release -si)
ARCH=$(uname -m | sed 's/x86_//;s/i[3-6]86/32/')
VER=$(lsb_release -sr)

# Check if the variable OS was retrieved and set correctly
if [ -z "$OS" ];
  then
    echoerr "Error.Could not detect system specifications."
    echoerr "Installation will terminate now."
    exit 1;
fi

# Print the system specifications
echoinfo $OS $VER x$ARCH
echo

# Check if ansible is installed in the system
command -v foo >/dev/null 2>&1 ||
{
  # Try to install git if system is Ubuntu or Centos
  echowarn  "Ansible is required but it's not installed.";
  echoinfo  "Ansible installation requires git.";
  git_check $OS
}

# Print Ansible installed version
command ansible --version 2>&1 | (head -n1 && tail -n1) | awk '{split($0, a, " "); print "[INFO] Ansible version installed", a[2]}'
