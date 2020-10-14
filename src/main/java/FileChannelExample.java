import java.io.FileInputStream;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

class FileChannelExample {
    private static final List<String> files = List.of(
            "/usr/src/nvidia-430.50/nvidia/nv-kernel.o_binary",
            "/usr/share/calibre/localization/locales.zip",
            "/usr/share/tesseract-ocr/4.00/tessdata/osd.traineddata",
            "/usr/share/spotify/spotify",
            "/usr/share/spotify/libcef.so",
            "/usr/share/mythes/th_en_AU_v2.dat",
            "/usr/share/mythes/th_de_DE_v2.dat",
            "/usr/share/mythes/th_de_CH_v2.dat",
            "/usr/share/mythes/th_en_US_v2.dat",
            "/usr/share/dict/polish",
            "/usr/share/fonts/opentype/noto/NotoSansCJK-Regular.ttc",
            "/usr/share/fonts/opentype/noto/NotoSerifCJK-Bold.ttc",
            "/usr/share/fonts/opentype/noto/NotoSerifCJK-Regular.ttc",
            "/usr/share/fonts/opentype/noto/NotoSansCJK-Bold.ttc",
            "/usr/lib/libgdal.so.20.3.2",
            "/usr/lib/x86_64-linux-gnu/libx265.so.146",
            "/usr/lib/x86_64-linux-gnu/libnvidia-glvkspirv.so.430.50",
            "/usr/lib/x86_64-linux-gnu/libcuda.so.430.50",
            "/usr/lib/x86_64-linux-gnu/libLLVM-9.so.1",
            "/usr/lib/x86_64-linux-gnu/libLLVM-8.so.1",
            "/usr/lib/x86_64-linux-gnu/libavcodec.so.57.107.100",
            "/usr/lib/x86_64-linux-gnu/libnvidia-rtcore.so.430.50",
            "/usr/lib/x86_64-linux-gnu/libwebkitgtk-3.0.so.0.22.17",
            "/usr/lib/x86_64-linux-gnu/libgs.so.9.26",
            "/usr/lib/x86_64-linux-gnu/libwebkit2gtk-4.0.so.37.39.4",
            "/usr/lib/x86_64-linux-gnu/libicudata.so.60.2",
            "/usr/lib/x86_64-linux-gnu/libmozjs-52.so.0.0.0",
            "/usr/lib/x86_64-linux-gnu/libnvoptix.so.430.50",
            "/usr/lib/x86_64-linux-gnu/libQt5WebKit.so.5.212.0",
            "/usr/lib/x86_64-linux-gnu/libnvidia-compiler.so.430.50",
            "/usr/lib/x86_64-linux-gnu/libicudata.a",
            "/usr/lib/x86_64-linux-gnu/libnvidia-glcore.so.430.50",
            "/usr/lib/x86_64-linux-gnu/libQtGui.so.4.8.7",
            "/usr/lib/x86_64-linux-gnu/libnvidia-eglcore.so.430.50",
            "/usr/lib/x86_64-linux-gnu/libjavascriptcoregtk-4.0.so.18.14.8",
            "/usr/lib/x86_64-linux-gnu/libnvidia-opencl.so.430.50",
            "/usr/lib/x86_64-linux-gnu/dri/r600_dri.so",
            "/usr/lib/x86_64-linux-gnu/dri/virtio_gpu_dri.so",
            "/usr/lib/x86_64-linux-gnu/dri/radeon_dri.so",
            "/usr/lib/x86_64-linux-gnu/dri/radeonsi_dri.so",
            "/usr/lib/x86_64-linux-gnu/dri/nouveau_vieux_dri.so",
            "/usr/lib/x86_64-linux-gnu/dri/vmwgfx_dri.so",
            "/usr/lib/x86_64-linux-gnu/dri/swrast_dri.so",
            "/usr/lib/x86_64-linux-gnu/dri/i965_dri.so",
            "/usr/lib/x86_64-linux-gnu/dri/kms_swrast_dri.so",
            "/usr/lib/x86_64-linux-gnu/dri/r200_dri.so",
            "/usr/lib/x86_64-linux-gnu/dri/iris_dri.so",
            "/usr/lib/x86_64-linux-gnu/dri/i915_dri.so",
            "/usr/lib/x86_64-linux-gnu/dri/nouveau_dri.so",
            "/usr/lib/x86_64-linux-gnu/dri/r300_dri.so",
            "/usr/lib/x86_64-linux-gnu/nvidia-430/xorg/libglxserver_nvidia.so.430.50"
    );

    public static void main(String[] args) throws Exception {
        List<MappedByteBuffer> mappedByteBuffers = files.stream()
                .map(path -> {
                    try {
                        return new FileInputStream(path);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .map(FileInputStream::getChannel)
                .map(fileChannel -> {
                    try {
                        return fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        mappedByteBuffers.forEach(mappedByteBuffer -> {
            System.out.println("...");
            CharBuffer charBuffer = Charset.defaultCharset().decode(mappedByteBuffer);
        });
        System.gc();
        Scanner scan = new Scanner(System.in);
        scan.nextLine();
    }
}
