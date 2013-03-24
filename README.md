JRankMirrors
------------
JRankMirrors allows you to get easily the five fastest Arch Linux mirrors.
It uses latest mirrors provided here : https://www.archlinux.org/mirrors/status/ and filter by fetching the
core.db.tar.gz file for each mirror.

Install
-------
1. Get the PKGBUILD or clone it (src/main/arch)
2. Create the package : makepkg
3. Install the package : makepkg -i

Usage
-----
Just launch in a terminal jrankmirrors. JRankMirrors will fetch the latest available mirrors and outputs the top five.
You may then paste these 5 lines in your /etc/pacman.d/mirrorlist file.

ChangeLog
---------
* 1.0.1
    - Shell script corrected.
    - No longer waits for all mirrors rating to finish.
* 1.0
    - Initial release.

License
-------
JRankMirrors is released under the BSD 2-Clause License.
