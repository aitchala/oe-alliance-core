SUMMARY = "Utilities for transponder & dvb-s/c blindscan"
SECTION = "base"
PRIORITY = "optional"
RDEPENDS_${PN} = "ncurses"

PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGES = "edision-blindscan-dvbs-utils-${MACHINE} edision-blindscan-dvbc-utils-${MACHINE}"

PROVIDES += "virtual/blindscan-dvbs virtual/blindscan-dvbc"
RPROVIDES_edision-blindscan-dvbs-utils-${MACHINE} += "virtual/blindscan-dvbs"
RPROVIDES_edision-blindscan-dvbc-utils-${MACHINE} += "virtual/blindscan-dvbc"

PV = "20181015"

SRC_URI = "http://source.mynonpublic.com/edision/edision-blindscan-utils-${MACHINE}-${PV}.zip"

S = "${WORKDIR}"

FILES_edision-blindscan-dvbs-utils-${MACHINE} = "${bindir}/blindscan"
FILES_edision-blindscan-dvbc-utils-${MACHINE} = "${bindir}/tda1002x"

do_install() {
	install -d ${D}${bindir}
	install -m 0755 ${S}/tda1002x ${D}${bindir}
	install -m 0755 ${S}/blindscan ${D}${bindir}
}

SRC_URI[md5sum] = "e9eed4bad2cee6bce3d4a6f1df8a89c9"
SRC_URI[sha256sum] = "919e6f00317a82c4dde5c07ee13beaf92b4076c022ce454b54dd0ee84d6a1ab8"

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot
INSANE_SKIP_edision-blindscan-dvbs-utils-${MACHINE} = "file-rdeps already-stripped"
INSANE_SKIP_edision-blindscan-dvbc-utils-${MACHINE} = "file-rdeps already-stripped"
