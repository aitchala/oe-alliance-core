SUMMARY = "Multi boot loader for enigma2"
MAINTAINER = "oe-alliance"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS = "freetype"

SRC_URI = "git://github.com/formiano/opdmultiboot.git;protocol=git"

inherit autotools-brokensep pkgconfig

S = "${WORKDIR}/git"

EXTRA_OEMAKE = " \
    'CFLAGS=${CFLAGS} \
    -I=${includedir}/freetype2 \
    ${@bb.utils.contains("MACHINE_FEATURES", "singlecore", "-DOPD_DEFAULT_TIMER=10" , "-DOPD_DEFAULT_TIMER=5", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "textlcd", "-DOPD_HAVE_TEXTLCD" , "", d)} \
    ${@bb.utils.contains("IMAGE_FSTYPES", "ubi", "-DOPD_FLASH_UBI" , "", d)} \
    ${@bb.utils.contains("IMAGE_FSTYPES", "jffs2", "-DOPD_FLASH_JFFS2" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dreambox", "-DOPD_DREAMBOX", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "mmc", "-DOPD_MMCBLK", "", d)} \
    -DOPD_KERNEL_MTD=\"/dev/${MTD_KERNEL}\"' \
    'LDFLAGS= -lfreetype ${LDFLAGS}' \
    "

do_install() {
    install -d ${D}/sbin
    install -m 755 ${S}/src/opd_multiboot ${D}/sbin
    install -m 644 ${S}/contrib/opd-multiboot-branding-helper.py ${D}/sbin
}

pkg_preinst_${PN}() {
#!/bin/sh
if mountpoint -q /usr/lib/enigma2/python/Plugins/Extensions/OPDBoot; then
    echo "OPDBoot will only install on main image."
    echo "Child image is running - canceling installation!"
    sleep 3
    exit 1
else
    echo "Main image is running - proceeding installation..."
    exit 0
fi
}

pkg_postinst_${PN}() {
rm /sbin/init
ln -s /sbin/opd_multiboot /sbin/init
}

pkg_postinst_${PN}_openbh() {
}

pkg_postrm_${PN}() {
rm /sbin/init
ln -s /sbin/init.sysvinit /sbin/init
} 
