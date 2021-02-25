package proyecto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;

import java.util.List;

public class Controller {

    public TableView tabla;
    public TableColumn columna_id;
    public TableColumn columna_nombre;
    public TableColumn columna_apellidos;
    public TableColumn columna_edad;
    public TableColumn columna_ciudad;

    public TextField tf_id;
    public TextField tf_nombre;
    public TextField tf_apellidos;
    public TextField tf_edad;
    public TextField tf_ciudad;

    public Button boton_insertar;
    public Button boton_modificar;
    public Button boton_borrar;

    public Label label_resultado;

    public void datosTabla() {

        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        columna_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        columna_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columna_apellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        columna_edad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        columna_ciudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));

        List<Datos> lista = session.createQuery("FROM Datos").getResultList();

        ObservableList<Datos> listaDatos = FXCollections.observableArrayList(lista);

        tabla.setItems(listaDatos);

        session.close();

        tabla.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 1) {
                    Datos datos = (Datos) tabla.getSelectionModel().getSelectedItem();
                    tf_id.setText(String.valueOf(datos.getId()));
                    tf_nombre.setText(datos.getNombre());
                    tf_apellidos.setText(datos.getApellidos());
                    tf_edad.setText(String.valueOf(datos.getEdad()));
                    tf_ciudad.setText(datos.getCiudad());
                }
            }
        });
    }

    public void insertarDatos() {

        try {

            Session session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();

            Datos datos = new Datos(0, tf_nombre.getText(), tf_apellidos.getText(), Integer.parseInt(tf_edad.getText()), tf_ciudad.getText());

            session.save(datos);
            session.getTransaction().commit();

            label_resultado.setText("Datos Insertados");

            datosTabla();

            tf_id.clear();
            tf_nombre.clear();
            tf_apellidos.clear();
            tf_edad.clear();
            tf_ciudad.clear();

        } catch (Exception ex) {
            label_resultado.setText("Formato Incorrecto");
        } finally {
            HibernateUtils.INSTANCE.cerrarSessionFactory();
        }
    }

    public void modificarDatos() {

        try {

            Session session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();

            Datos datos = (Datos) session.load(Datos.class, Integer.parseInt(tf_id.getText()));

            datos.setNombre(tf_nombre.getText());
            datos.setApellidos(tf_apellidos.getText());
            datos.setEdad(Integer.parseInt(tf_edad.getText()));
            datos.setCiudad(tf_ciudad.getText());

            session.update(datos);
            session.getTransaction().commit();

            label_resultado.setText("Datos Modificados");

            datosTabla();

            tf_id.clear();
            tf_nombre.clear();
            tf_apellidos.clear();
            tf_edad.clear();
            tf_ciudad.clear();

        } catch (Exception ex) {
            label_resultado.setText("Formato Incorrecto");
        } finally {
            HibernateUtils.INSTANCE.cerrarSessionFactory();
        }
    }

    public void borrarDatos() {

        try {

            Session session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();

            Datos datos = (Datos) session.load(Datos.class, Integer.parseInt(tf_id.getText()));

            session.delete(datos);
            session.getTransaction().commit();

            label_resultado.setText("Datos Borrados");

            datosTabla();

            tf_id.clear();
            tf_nombre.clear();
            tf_apellidos.clear();
            tf_edad.clear();
            tf_ciudad.clear();

        } catch (NumberFormatException ex) {
            label_resultado.setText("Formato Incorrecto");
        } finally {
            HibernateUtils.INSTANCE.cerrarSessionFactory();
        }
    }

    public void initialize() {
        datosTabla();
    }
}
