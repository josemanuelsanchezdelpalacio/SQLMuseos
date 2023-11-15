package entities;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "obras", schema = "museos")
public class ObrasEntity {
    private Integer id;
    private String titulo;
    private Date fecha;
    private Integer idMuseo;
    private Integer idAutor;
    private MuseosEntity museosByIdMuseo;
    private AutoresEntity autoresByIdAutor;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "titulo", nullable = true, length = 200)
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Basic
    @Column(name = "fecha", nullable = true)
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Basic
    @Column(name = "id_museo", nullable = false)
    public Integer getIdMuseo() {
        return idMuseo;
    }

    public void setIdMuseo(Integer idMuseo) {
        this.idMuseo = idMuseo;
    }

    @Basic
    @Column(name = "id_autor", nullable = false)
    public Integer getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Integer idAutor) {
        this.idAutor = idAutor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ObrasEntity that = (ObrasEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (titulo != null ? !titulo.equals(that.titulo) : that.titulo != null) return false;
        if (fecha != null ? !fecha.equals(that.fecha) : that.fecha != null) return false;
        if (idMuseo != null ? !idMuseo.equals(that.idMuseo) : that.idMuseo != null) return false;
        if (idAutor != null ? !idAutor.equals(that.idAutor) : that.idAutor != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (titulo != null ? titulo.hashCode() : 0);
        result = 31 * result + (fecha != null ? fecha.hashCode() : 0);
        result = 31 * result + (idMuseo != null ? idMuseo.hashCode() : 0);
        result = 31 * result + (idAutor != null ? idAutor.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_museo", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    public MuseosEntity getMuseosByIdMuseo() {
        return museosByIdMuseo;
    }

    public void setMuseosByIdMuseo(MuseosEntity museosByIdMuseo) {
        this.museosByIdMuseo = museosByIdMuseo;
    }

    @ManyToOne
    @JoinColumn(name = "id_autor", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    public AutoresEntity getAutoresByIdAutor() {
        return autoresByIdAutor;
    }

    public void setAutoresByIdAutor(AutoresEntity autoresByIdAutor) {
        this.autoresByIdAutor = autoresByIdAutor;
    }
}
