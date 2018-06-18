package com.martin.calculadorasimples;

import java.util.ArrayList;
import java.util.List;

public class Filme {

    private Long id;
    private String title;
    private String image;
    private List<String> genre = new ArrayList<>();
    private String rating;
    private String releaseYear;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String imagem) { this.image = imagem; }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public String getGenresSTR(){

        String gen= "";
        for (String gen_array : this.genre){
            gen = gen.concat(gen_array.toString()+ ", ");
        }
        if(gen.length() > 0)
            gen = gen.substring(0,gen.length() - 2);
        return gen;
    }

    public void setGenresSTR(String valor){
        String[] gen = valor.split(", ");
        for(String str : gen){
            this.addgen(new String(str));
        }
    }

    public void addgen(String genre){
        this.genre.add(genre);
    }
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) { this.rating = rating; }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Filme(){

    }
    public Filme(long id, String title, String image, String genre, String rating, String releaseYear) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.setGenresSTR(genre);
        this.rating = rating;
        this.releaseYear = releaseYear;
    }

    public Filme(String title, String image, String genre, String rating, String releaseYear) {
        this.title = title;
        this.image = image;
        this.setGenresSTR(genre);
        this.rating = rating;
        this.releaseYear = releaseYear;
    }

    @Override
    public String toString() {
        return "Filme{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", genre=" + genre +
                ", rating='" + rating + '\'' +
                ", releaseYear='" + releaseYear + '\'' +
                '}';
    }
}
