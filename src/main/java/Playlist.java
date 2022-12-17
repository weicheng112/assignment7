import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

public class Playlist {
    private ArrayList<Song> listOfSongs;

    public ArrayList<Song> getListOfSongs(){
        return listOfSongs;
    }

    public Playlist(){
        listOfSongs = new ArrayList<Song>();
    }

    public void addSong(Song s){
        this.listOfSongs.add(s);
    }

    public void deleteSong(Song s){
        if (listOfSongs.contains(s)){
            listOfSongs.remove(s);
        }else {
            System.out.printf("%s is not in the playlist!",s.toString());
        }
    }

    public Playlist mergeTwoPlaylist(List<Song> songList){
        List<Song> oldList = new ArrayList<>();
        for (Song s: listOfSongs){
            oldList.add(s);
        }
        for (Song s: songList){
            oldList.add(s);
        }
        LinkedHashSet<Song> hashSet = new LinkedHashSet<>(oldList);
        List<Song> result = new ArrayList<>(hashSet);
        Playlist pl = new Playlist();
        for(Song s:result) {
            pl.addSong(s);
        }
        return pl;
    }

    public Playlist sortByLiked(){
        ArrayList<Song> likedSong = new ArrayList<Song>();
        ArrayList<Song> unlikedSong = new ArrayList<>();
        for (int i = 0; i<listOfSongs.size();i++){
            if(listOfSongs.get(i).isLiked()){
                likedSong.add(listOfSongs.get(i));
            }else {
                unlikedSong.add(listOfSongs.get(i));
            }
        }

        for(Song s:unlikedSong){
            likedSong.add(s);
        }

        Playlist playlist = new Playlist();
        for ( Song s: likedSong){
            playlist.addSong(s);
        }
        return playlist;

    }

    public void shuffleTheList(){
        Collections.shuffle(listOfSongs);
    }
    public Playlist createRandomPlaylistByGenre(String g){
        ArrayList<Song> newList = new ArrayList<>();
        Playlist playlist = new Playlist();
        for(Song s: listOfSongs){
            if(s.getGenre().equals(g)){
                newList.add(s);
            }
        }

        for(Song s:newList){
            playlist.addSong(s);
        }
        return playlist;
    }

    public String toXML(){
        String s1="";
        for(Song s: listOfSongs){
            String s2 = String.format("<song><title>%s</title><artist>%s</artist><album>%s</album></song>",s.name,s.getPerformer().name,s.getAlbum().name);
            s1=s1.concat(s2);
        }

        return s1;
    }

    public String toJSON(){
        String s1=" {"+"\""+"songs"+"\""+" : [";
        String first=String.format("{\"title\":\"%s\",\"artist\":\"%s\",\"album\":\"%s\"}\n" , listOfSongs.get(0).name,listOfSongs.get(0).getPerformer().name,listOfSongs.get(0).getAlbum().name);
        s1 = s1.concat(first);
        String s3="";
        for(Song s:listOfSongs.subList(1,listOfSongs.size())){
            String s2 = String.format(",{\"title\":\"%s\",\"artist\":\"%s\",\"album\":\"%s\"}\n",s.name,s.getPerformer().name,s.getAlbum().name);
            s3=s3.concat(s2);
        }
        s1=s1.concat(s3);
        s1=s1.concat("]}");

        return s1;
    }
}
