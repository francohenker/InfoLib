package modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "bibliotecario")
public class Bibliotecario extends Usuario{

    protected Bibliotecario(){    }

    public Bibliotecario(String dni, String nombre, String apellido){
        super(dni, nombre, apellido);
    }


    public void createPrestamo(Usuario usuario, Libro libro){

    }

    public void addLibro(Libro libro){

    }

    public void removeLibro(Libro libro){

    }
}
