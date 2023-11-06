package com.example.spinnerpersonalizado

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView

class MainActivity : AppCompatActivity(),  AdapterView.OnItemSelectedListener {
    //Utilizo tres vectores con los elementos que necesito
    private val equipos = arrayOf("FCB", "Betis", "Celta")
    private val descripciones = arrayOf(
        "Fútbol club Barcelona",
        "Real Betis Balompie",
        "Real Club Celta de Vigo"
    )

    private val imagenes = intArrayOf(
        R.drawable.fcb,
        R.drawable.betis,
        R.drawable.celta

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val selectorEquipos = findViewById<Spinner>(R.id.spinner)
        val adaptadorPersonalizado = AdaptadorPersonalizado(this, R.layout.lineaspiner, equipos)
        selectorEquipos.adapter = adaptadorPersonalizado
        selectorEquipos.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val c = view?.findViewById<TextView>(R.id.nombre)
        val seleccion = findViewById<TextView>(R.id.equipoSeleccionado)
        seleccion.text = c?.text
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        val seleccion = findViewById<TextView>(R.id.equipoSeleccionado)
        seleccion.text = "nada seleccionado!"
    }

    /**
     * Creo una subclase de array adapter para personalizar el adaptador
     *
     * Las clases internas son útiles cuando deseas agrupar lógica relacionada en un solo lugar,
     * proporcionar un nivel adicional de encapsulación o cuando la clase interna necesita acceder
     * a miembros privados de la clase contenedora.
     *
     * Es importante notar que, en Kotlin, una clase interna anidada es interna solo si se declara
     * con la palabra clave inner. De lo contrario, se considera una clase interna estática y no
     * puede acceder a los miembros de la clase contenedora sin una instancia de la misma.
     *
     *
     */

    private inner class AdaptadorPersonalizado(
        context: Context,
        resource: Int,
        objects: Array<String>
    ) : ArrayAdapter<String>(context, resource, objects) {
        //Constructor de mi adaptador paso el contexto (this)
        // el layout, y los elementos

        /**
         * Reescribo el método getDropDownView para que me devuelva una fila personalizada en la
         * lista desplegable en vez del elemento que se encuentra en esa posición
         * @param posicion
         * @param ViewConvertida
         * @param padre
         * @return
         */
        override fun getDropDownView(
            position: Int,
            convertView: View?,
            parent: ViewGroup
        ): View {


            // Llama a la función para crear la fila personalizada y la devuelve
            return crearFilaPersonalizada(position, convertView, parent)
        }

        override fun getView(
            position: Int,
            convertView: View?,
            parent: ViewGroup
        ): View {
            // Este método se llama para mostrar una vista personalizada en el elemento seleccionado

            // Llama a la función para crear la fila personalizada y la devuelve
            return crearFilaPersonalizada(position, convertView, parent)
        }

        /**
         * Método que me crea mis filas personalizadas pasando como parámetro la posición
         * la vista y la vista padre
         * @param position
         * @param convertView
         * @param parent
         * @return
         */
        private fun crearFilaPersonalizada(
            position: Int,
            convertView: View?,
            parent: ViewGroup
        ): View {

            // Crea un objeto LayoutInflater para inflar la vista personalizada desde un diseño XML
            val layoutInflater = LayoutInflater.from(context)

            //Declaro una vista de mi fila, y la preparo para inflarla con datos
            // Los parametros son: XML descriptivo
            // Vista padre
            // Booleano que indica si se debe ceñir a las características del padre
            val rowView = convertView ?: layoutInflater.inflate(R.layout.lineaspiner, parent, false)

            //Fijamos el nombre de la ciudad
            rowView.findViewById<TextView>(R.id.nombre).text = equipos[position]

            //Fijamos la descripción de la ciudad
            rowView.findViewById<TextView>(R.id.descripcion).text = descripciones[position]

            //Fijamos la imagen de la ciudad
            rowView.findViewById<ImageView>(R.id.imagenCiudad).setImageResource(imagenes[position])

            // Devuelve la vista de fila personalizada
            return rowView
        }
    }
}