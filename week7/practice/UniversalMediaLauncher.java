public class UniversalMediaLauncher {
    interface Playable {
        String play();
        String play(int fromSecond);
        String pause();
    }

    static abstract class MediaFile {
        private static int fileCount;
        private final String fileId;

        MediaFile() {
            fileCount++;
            fileId = String.format("MF-%04d", 1000 + fileCount);
        }

        abstract String getFormatInfo();

        String getFileId() {
            return fileId;
        }
    }

    static class AudioFile extends MediaFile implements Playable {
        private String title;

        AudioFile(String title) {
            this.title = title;
        }

        @Override
        String getFormatInfo() {
            return "Audio file, ID: " + getFileId();
        }

        @Override
        public String play() {
            return "Playing audio: " + title;
        }

        @Override
        public String play(int fromSecond) {
            return "Playing audio: " + title + " from " + (fromSecond / 60) + ":"
                    + String.format("%02d", fromSecond % 60);
        }

        @Override
        public String pause() {
            return "Paused audio: " + title;
        }
    }

    static class Podcast implements Playable {
        private String showName;
        private int episodeNumber;

        Podcast(String showName, int episodeNumber) {
            this.showName = showName;
            this.episodeNumber = episodeNumber;
        }

        @Override
        public String play() {
            return "Streaming episode " + episodeNumber + " of " + showName;
        }

        @Override
        public String play(int fromSecond) {
            return play() + " from " + fromSecond + " seconds";
        }

        @Override
        public String pause() {
            return "Paused episode " + episodeNumber + " of " + showName;
        }
    }

    static void launchAll(Playable[] items) {
        for (Playable item : items) {
            System.out.println(item.play());
        }
    }

    public static void main(String[] args) {
        AudioFile audio = new AudioFile("Morning Jazz");
        Podcast podcast = new Podcast("Tech Talk", 12);
        System.out.println(audio.play());
        System.out.println(audio.play(30));
        System.out.println(audio.getFormatInfo());
        System.out.println(podcast.play());
        Playable reference = audio;
        System.out.println(reference.play());
        launchAll(new Playable[]{reference, podcast});
    }
}