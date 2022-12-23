package com.example.gstreamerworkshop;

import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.Pipeline;
import org.freedesktop.gstreamer.Version;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class VideoProfileController {
    private static Pipeline pipeline;

    @GetMapping("/profile")
    public void split() {
        /**
         * Set up paths to native GStreamer libraries - see adjacent file.
         */
        Utils.configurePaths();

        /**
         * Initialize GStreamer. Always pass the lowest version you require -
         * Version.BASELINE is GStreamer 1.8. Use Version.of() for higher.
         * Features requiring later versions of GStreamer than passed here will
         * throw an exception in the bindings even if the actual native library
         * is a higher version.
         */
        Gst.init(Version.BASELINE, "BasicPipeline");

        List<String> commandLineArgs = new ArrayList<>();

        commandLineArgs.add("filesrc");
        commandLineArgs.add("location=/Users/fewwcom/Downloads/aaaaa.mov");
        commandLineArgs.add("! qtdemux");
        commandLineArgs.add("! splitmuxsink");
        commandLineArgs.add("location=/Users/fewwcom/Downloads/aaaaa-%05d.mov");
        commandLineArgs.add("max-size-bytes=21000000");

        /**
         * Use Gst.parseLaunch() to create a pipeline from a GStreamer string
         * definition. This method returns Pipeline when more than one element
         * is specified.
         */
        pipeline = (Pipeline) Gst.parseLaunch(String.join(" ", commandLineArgs));

        /**
         * Start the pipeline.
         */
        pipeline.play();

        /**
         * GStreamer native threads will not be taken into account by the JVM
         * when deciding whether to shut down, so we have to keep the main thread
         * alive. Gst.main() will keep the calling thread alive until Gst.quit()
         * is called. Here we use the built-in executor to schedule a quit after
         * 10 seconds.
         */
        Gst.getExecutor().schedule(Gst::quit, 10, TimeUnit.SECONDS);
        Gst.main();
    }
}
