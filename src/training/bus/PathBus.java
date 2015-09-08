package training.bus;

import training.unrc.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Representante de el recorrido de una linea.
 */
public class PathBus extends Activity {

	private Bundle args;
	private TextView numberOfLine;
	private TextView pathOfLine;
	private int position;
	private TextView aditionalInfo;

	private String oneRed = "Villa Golf Club -> Av. Libertador Don José De San Martín -> San Martín -> (Plaza Roca) -> Belgrano -> Alberdi -> Rivadavia -> Boulevard Roca (Estación Ferrocarril) -> Ameghino -> Pedro Goyena -> Leandro N. Alem -> P.Peron Este -> Chiclana -> Pedro Goyena -> Malabia -> P.Peron -> Joaquín V. González -> V. López -> I . Pirovano -> Entre Ríos -> M.Rosas -> Luis Pasteur -> Ameghino -> Bv. Roca -> Lavalle -> V. Sarsfield -> (Plaza Roca) -> Buenos Aires -> Trejo Y Sanabria -> (Cementerio) -> G.Guzman -> Colectora -> Rotonda Golf -> Av. Libertador Don José De San Martín (Villa Golf Club)Punta de linea";
	private String oneGreen = "Villa Dalcar -> Partiendo Por Tejerina -> Cervantes -> San Martín -> Plaza Roca Belgrano -> Alberdi -> Rivadavia -> Bv. Roca -> (Estación De Ferro Carril) -> Ameghino -> Tucumán -> Leandro N Alen -> Entre Ríos -> Pirovano -> Vicente López -> Joaquín V. González -> P.Peron -> J.D. De Solís -> O. Andrade -> Montevideo -> Int. Daguerre -> Liniers -> O. Andrade -> Almafuerte -> P. Goyena -> Bv. Ameghino -> Bv. Roca -> Lavalle -> V. Sarsfield -> (Plaza Roca) Buenos Aires -> Trejo Y Sanabria -> (Cementerio) -> G.Guzman -> Cervantes -> Villa Dalcar ->  Punta de linea";
	private String twoRed = "B. Las Ferias -> Sabattini -> Suipacha -> Gral. Soler -> P.Peron (O) -> 9 De Julio -> Echeverria -> Pringles -> Bv. Ameghino -> Bv. Roca Lavalle -> Vélez Sarsfield -> H. Yrigoyen -> 25 De Mayo -> Sobremonte -> G. Alonso -> Av. España -> Plaza San Martín (Puente Carretero) -> Av.M.T. De Alvear -> Ruta 36 -> Universidad Nacional Punta de linea.-> Regresando Por Ruta 36 -> Av. M.T. De Alvear (Puente Carretero) -> Av. España -> (Plaza San Martín) -> Mugnaini -> Cabrera -> Rioja -> Av. Italia -> Sabattini -> Mosconi -> Hospital Central -> Sarmiento -> Yanquetruz -> Ludovico Cuaranta -> Laguna Leuvuco -> Ruta 8 -> Sabattini -> - Punta de linea";
	private String two = "Mosconi Y Almirante Brown -> Pedro Vargas -> Sarmiento -> Mosconi -> Hsp. Central -> Av. Sabattini -> Suipacha -> Gral. Soler -> P. Perón (O) -> 9 De Julio -> Echeverria -> Pringles -> Bv. Ameghino -> Bv. Roca Lavalle -> Vélez Sarsfield -> H. Yrigoyen -> 25 De Mayo -> Sobremonte -> G. Alonso -> Av. España -> Plaza San Martín (Puente Carretero) -> Av.M.T. De Alvear -> Ruta 36 -> Universidad Nacional (Punta De Línea).->Regresando Por Ruta 36 -> Av. M.T. De Alvear (Puente Carretero) -> Av. España -> (Plaza San Martín) Av. Mugnaini -> Cabrera -> Rioja -> Av. Italia -> Sabattini Mosconi -> Hospital Central -> Mosconi Y A.Brown -> Punta de linea";
	private String three = "Echeverria -> Pringles -> H. Yrigoyen -> 25 De Mayo -> Sobremonte -> G. Alonso -> España -> Plaza San Martín -> Puente Carretero -> Av. M.T. Alvear -> Av. Garibaldi -> Buteler (Mercado De Abasto) -> Rotonda Sobre A005 -> Dr. Maidana -> Dest. Policía Tres Acequias -> (Punta De Línea).  Regresando Por -> Dr. Maidana -> Rotonda Sobre A005 -> (Puente Islas Malvinas) 	Rotonda -> Tierra Del Fuego -> Buteler (Mercado De Abasto) -> Av. Garibaldi ->Av. M.T. Alvear -> Puente Carretero -> Av. España -> Int. H. Mugnaini -> Cabrera -> Echeverria Punta de linea.";
	private String four = "I.P.V. Fénix -> Caracas -> Pje. Ipe -> Decouvette -> D. Mendocinas -> Dr. Martín De Alva -> Pje San Lorenzo -> Tomas Guido -> San Lorenzo -> Lavalle -> V. Sarsfield -> Buenos Aires -> Alvear -> S. Vera -> Maipú -> Roma -> Dinkeldein -> Fitz Roy -> Maipú -> Perito Moreno -> Marconi -> Dr. Carlos Rodríguez -> Guardias Nacionales -> Estrada -> 11 De Noviembre -> Dr. Carlos Rodríguez -> Río Segundo -> (Pileton Municipal) Punta de linea.-> Regresando Por -> Río Grande -> G. López -> Río Colorado -> Río Nihuil -> Río Juramento -> G. López -> Dr. Carlos Rodríguez -> 11 De Noviembre -> Estrada -> Guardias Nacionales -> Dinkeldein -> Roma -> Maipú -> San Juan -> Constitución -> San Martín -> Belgrano -> Alberdi ->Pte. Perón -> Laprida -> (Barrio Fénix) -> Sucre -> Sarmiento -> Pedro Vargas -> Alberdi -> Mosconi -> San Lorenzo ->Pedro Vargas -> Juan De Dios López -> Ipv Fénix ->  Punta de linea.";
	private String five = "U.N.R.C -> Ruta 36 -> Muñiz -> Q. Porreca -> M.S. De Thompson -> Colombia -> (Puente Juan Filloy) -> Av. Costanera -> Balcarce -> V. Sarsfield -> Buenos Aires -> Cabrera -> Paunero -> Arturo M. Bas -> Trejo Y Sanabria -> Rioja -> Blas Parera -> Urquiza -> Roque Sáenz Peña -> P. Perón Oeste -> Dinkeldein -> Carlos Gardel -> Arrayanes -> Vasco Zacarías -> F.M.Esquiu -> J.M.Estrada -> Paso De Los Andes -> Geronimo Del Barco -> Ordóñez -> Soler -> Suipacha -> Maipú -> Echeverria -> Alvear -> San Martín -> Belgrano -> Balcarce -> Av. Costanera -> Puente Juan Filloy -> Muñiz -> Ruta 36 -> U.N.R.C -> Punta de linea.";
	private String six = "Mosconi -> San Lorenzo -> Juan De Dios López -> Caracas -> (I.P.V. Fénix) -> Pje.Ipe -> Decouvette -> Patricias Mendocinas -> Pje San Lorenzo -> Tomas Guido -> San Lorenzo -> Pte. Perón (E) -> Lavalle -> V. Sarsfield -> H. Yrigoyen -> 25 De Mayo -> Sobremonte -> Alonso -> Av. España -> (Plaza San Martín) -> Cont. Av. España -> (Puente Carretero) -> Av. M.T. De Alvear -> Chile -> Muñiz -> F.Q.Porreca -> (El Acordeón) -> Av. Reforma Universitaria -> Rutas 36 -> Universidad Nacional R.C.-> Punta de linea. Universidad Nacional R.C. -> Rutas 36 -> Av. Reforma Universitaria -> (El Acordeón) -> Fray Q. Porreca -> Av. Marcelo T. De Alvear ->(Puente Carretero) -> Av. España -> (Plaza San Martín) -> Cont. Av. España-> Constitución -> San Martín -> (Paza Roca) -> Belgrano -> Alberdi -> P.Peron (E) -> Laprida -> (Barrio Fénix) -> Sucre -> Sarmiento-> Pedro Vargas -> Alberdi -> Mosconi -> (Hospital Central) -> Punta de linea.";
	private String seven = "Boulevard Buteler -> Roberto Pairo ->Puente Colgante -> Río Negro -> Lago Nahuel Huapi -> José M. Estrada -> Guardias Nacionales -> Rosario De Santa Fe -> Río Segundo -> Río Colodaro ->Pedro Zanni -> Arroyo Piedra Blanca -> G. Marconi -> Marconi -> Las Postas -> Ituzaingo -> Fitz Roy -> Dinkeldein -> Roma -> Maipú -> San Juan -> Caseros -> Alberdi -> V.Sarsfield -> Buenos Aires -> Cabrera -> Sadi Carnot -> Ituzaingo -> Pte. Perón (O)-> Geronimo Del Barco -> Juárez Celman -> G. Soler -> Remedio De Escalada -> Crnel Olmedo -> Pizarro -> Maipú -> Zarco -> Dinkeldein -> I. Biassi -> L. Rinaudi -> Virrey Verti -> Estrada -> Esquiu -> Vasco Zacarías -> Los Arrayanes -> Carlos Gardel -> Dinkeldein -> Martín Quenon -> Maipú -> G. De La Quintana -> Gral. Soler -> Suipacha -> Maipú -> Echeverria -> Alvear -> Sebastian Vera -> Maipú -> Roma -> Dinkeldein -> Fitz Roy -> Maipú -> Rosario De Santa Fe -> Guardias Nacionales -> Gob. M. López -> Río Grande -> Río Segundo -> José M. Estrada -> Lago Nahuel Huapi -> Río Negro -> Puente Colgante -> Roberto Pairo -> Boulevard Buteler ->Punta de linea.";
	private String eightGreen = "Rutas 36 -> U.N.R.C. -> Av. Reforma Universitaria -> F.Q. Porreca -> Av.M.T. De Alvear -> (Puente Carretero) (Plaza San Martín ) -> Av. España -> Constitución -> San Juan -> Caseros -> Alberdi -> V. Sarsfield -> (Plaza Roca) -> Buenos Aires -> Trejo Y Sanabria -> G.Guzman -> Cervantes ->Tejerina -> Ruta A005 -> (Colegio Bernardino Rivadavia)-> Ruta A005 ->Rotonda ->Ruta Nº30-> Ruta Nº1 -> Villa Golf Club -> Av. San Martín -> (Plaza Roca) -> Sobremonte -> Alonso -> Av. España -> (Plaza San Martín) -> (Puente Carretero) -> Av. M.T.De Alvear -> Chile -> F. Muñiz -> F. Q. Porreca -> Av. Reforma Universitaria -> Rutas 36 -> U.N.R.C. -> Palestro -> Punta de linea.";
	private String eightRed = "Rutas 36 -> U.N.R.C. Av. Reforma Universitaria -> F.Q. Porreca -> M.T. De Alvear -> (Puente Carretero) Av. España -> Constitución -> San Juan -> Caseros ->Alberdi -> V. Sarsfield -> Buenos Aires -> Trejo Y Sanabria -> G. Guzmán -> Colectora ->Rotonda -> San Martín -> Villa Gof Club -> Ruta Nº1 -> Ruta 30 -> Rotonda -> Ruta A005 -> Tejerina -> Cervantes -> Av. Libertador San Martín -> (Plaza Roca) -> Sobremonte -> Alonso -> Av. España ->(Plaza San Martín) -> (Puente Carretero) -> Av. M.T.De Alvear -> Chile -> F. Muñiz -> Fray Q. Porreca -> Av. Reforma Universitaria -> Rutas 36 -> U.N.R.C. -> Palestro -> Punta de linea.";
	private String nineGreen = "U.N.R.C. -> Rutas 36 -> Muñiz -> Ecuador -> A. Mozart -> E. Jenner -> Perú -> R. Payro -> Obispo Buteler -> Tierra Del Fuego -> Isabel La Católica -> Córdoba -> Ranqueles -> Av. M.T. Alvear -> Puente Carretero -> Plaza San Martín -> Av. España -> Constitución -> (Plaza Roca) -> Belgrano -> Alberdi -> Rivadavia -> Bv. Roca -> (Estación De Ferrocarril) -> Ameghino -> Tucumán -> L.Alen -> Entre Ríos -> Pirovano -> V. López -> J.B. González -> P. Perón -> Juan Díaz De Solís -> O. Andrade -> Montevideo -> Int. Daguerre -> Azopardo -> Salta -> Colombre -> Caracas -> ( Ipv ) -> Pje Ipe -> Quina Roja -> Kantuta -> Azopardo -> Guayaquil -> San Lorenzo -> P.Peron -> Lavalle -> V. Sarsfield -> H. Yrigoyen -> 25 De Mayo -> Sobremonte -> G. Alonso -> Av. España -> Plaza San Martín -> Puente Carretero -> Av. M.T. Alvear -> Isabel La Católica -> Tierra Del Fuego -> Obispo Buteler -> (Mercado De Abasto) -> Roberto Pairo -> Perú -> Eduardo Jenner -> A. Mozart -> Ecuador -> F.Muñiz -> Rutas 36 -> Universidad Nacional -> Punta de linea.";
	private String nineRed = "U.N.R.C. -> Rutas 36 -> Muñiz -> Ecuador -> A. Mozart -> E. Jenner -> Perú -> R.Pairo -> Obispo Buteler -> Tierra Del Fuego -> Isabel La Católica -> Córdoba -> Ranqueles -> Av. M.T. Alvear -> Puente Carretero -> Av. España -> Plaza San Martín -> Av. España -> Constitución -> (Plaza Roca) -> Belgrano -> Alberdi -> Rivadavia -> Bv. Roca -> (Estación De Ferrocarril) -> Ameghino -> Pedro Goyena -> Laprida -> Guayaquil -> Liniers -> Azopardo -> Kancuta -> Quina Roja ->Pje Ipe -> Caracas -> Colombres -> Almirante Brown -> P. Perón -> J. B. González -> V. López -> Pirovano -> Entre Ríos -> Manuelita Rosas -> Pasteur -> Bv. Ameghino -> Bv. Roca -> Lavalle -> V. Sarsfield -> H. Yrigoyen -> 25 De Mayo -> Sobremonte -> G. Alonso -> Av. España -> (Puente Carretero) -> Av. M.T. De Alvear -> Isabel La Católica -> Tierra Del Fuego -> Obispo Buteler -> (Mercado De Abasto) -> Roberto Pairo -> Perú -> Eduardo Genner -> A. Mozart -> Ecuador -> F.Muñiz -> Enlace Rutas 8 Y 36 -> Universidad Nacional -> Punta de linea.";
	private String ten = "J.B. González Y Catamarca -> Malabia -> Adelia María -> Montevideo -> Salta -> Colombres -> Caracas -> I.P.V. ->Pje Ipe Quina Roja -> Kantuta -> Azopardo -> Tomas Guido -> San Lorenzo -> Pte Perón € -> Lavalle -> V. Sarsfield -> (Plaza Roca) -> Buenos Aires -> Cabrera -> Sadi Carnot -> Ituzaingo -> P. Perón (O) -> G. Del Barco -> J. Celman -> G. Soler -> Pizarro -> Maipú -> Sarco -> Dinkeldein -> Quenon -> Maipú -> Echeverria -> Alvear -> San Martín -> (Plaza Roca) -> Belgrano -> Alberdi -> P.Peron -> Laprida -> Tomas Guido Azopardo -> Kantuta -> Quina Roja -> Pje Ipe -> I.P.V. -> Caracas -> Colombres -> Salta -> Montevideo -> Adelia María -> J.B. González Y Catamarca -> Punta de linea.";
	private String eleven = "Av. J.F.Kennedy -> Manulita Rosas -> Guayaquil -> J.B. González -> Catamarca -> Malabia -> Adelia María -> Montevideo -> Salta -> J.Colombres -> Caracas -> I.P.V. ->Pje Ipe -> Quina Roja -> Kancuta -> Azopardo -> Tomas Guido -> San Lorenzo -> Presidente Perón -> Lavalle -> V.Sarsfield -> (Plaza Roca) -> Buenos Aires -> Cabrera -> Paunero -> Dinqueldein -> Zarco -> Maipú -> Pizarro -> Soler -> Suipacha -> Maipú -> Echeverria -> Alvear -> San Martín -> (Plaza Roca) -> Belgrano -> Alberdi -> Pte. Perón -> Laprida -> Tomas Guido -> Guayaquil ->Azopardo -> Kantuta -> Quina Roja -> Pje Ipe -> I.P.V. -> Caracas -> J. Colombres -> Salta -> Montevideo -> Adelia María -> Yapeyu -> Av.J.F.Kennedy ->Punta de linea";
	private String twelve = "U.N.R.C -> Ruta Nº36 -> Segurola -> Cuba -> Muñiz -> Paúl Groussac -> Achalay -> Estados Unidos -> Av. M.T.De Alvear -> (Puente Carretero) -> Av. España -> (Plaza San Martín) -> Constitución -> Plaza Roca -> San Martín -> Belgrano -> Alberdi -> Pte Perón (E)-> Alberdi -> Fray Donatti -> Concejal Aizamendi -> Sarmiento -> Pedro Vargas -> Alberdi -> Mosconi -> Hospital Central. Punta de linea.Hospital Central -> Mosconi -> Almirante Brown -> Juan D. López -> L. Alem -> Ipv Fénix -> Pje Ipe -> Quina Roja -> Kantuta -> Azopardo -> Patricias Mendocinas -> M.Alba -> Pje San Lorenzo -> Tomas Guido -> H.Irigoyen -> 25 De Mayo -> Sobremonte -> Alonso -> Av. España -> (Puente Carretero) -> Av. M.T.De Alvear -> Estados Unidos ->Achalay ->Paúl Groussac -> Muñiz -> Cuba -> Segurola -> Ruta Nº36 -> U.N.R.C -> Punta de linea.";
	private String thirteen = "Ruta Nº 36 -> Av. Reforma Universitaria -> Fray Quirico Porreca -> Mariquita S. De Thomson -> Colombia -> F. Muñiz -> (Puente Juan Filloy) -> Av. Costanera -> Balcarce -> Corrientes -> Lavalle -> Paraná -> Alberdi -> V. Sarsfield -> Plaza Roca -> Buenos Aires -> Cabrera -> Sadi Carnot -> Ituzaingo -> P.Peron O -> G.Del Barco -> Paso De Los Andes -> Estrada -> Fray M.Esquiu -> V. Zacarías  -> Arrayanes -> Carlos Gardel -> Dinkeldein -> Quenon -> J.M. Estrada -> Pte. Perón Oeste -> (Hipódromo) -> Roque Sáenz Peña -> Urquiza -> Blas Parera -> Rioja -> Trejo Y Sanabria -> Mártires Riocuartences -> Echeverria -> Alvear -> San Martín -> Sobremonte -> Caseros -> Balcarce -> Av. Costanera -> (Puente Juan Filloy) -> F. Muñiz -> F.Quirico Porreca -> Av. Reforma Universitaria -> Rutas Nº 36 -> (Universidad Nacional R.C.) -> Punta de linea.";
	private String fourteen = "San Lorenzo -> Gobernador Rodríguez -> Alberdi -> Mosconi -> Av.Sabattini -> Paso De Los Andes -> J.M.Estrada -> F.M.Esquiu -> Dinkeldein -> C.Gardel -> Arrayanes -> Cuenca -> Antonio Lucero -> Martín Quenon -> Enlace Rutas A005 -> San Martín -> (Plaza Roca) -> Sobremonte -> G.Alonso -> Alvear -> Mugnaini -> Andrés Dadone (Terminal) -> Pedro Zanni -> Carlos Rodríguez -> Hospital San Antonio De Padua -> Continua Dr. Carlos Rodríguez -> Río Segundo -> Punta de linea. -> Regresando: Comandante Giachino -> Av. España -> Plaza San Martín -> Jaime Gil -> Cabrera -> Buenos Aires -> Trejo Y Sanabria -> G. Guzmán -> Enlace Rutas A005 -> Martín Quenon -> A. Lucero -> Cuenca -> Arrayanes -> Carlos Gardel -> Dinkeldein -> Esquiu -> J.M. Estrada -> Paso De Los Andes -> Av.A. Sabattini -> Mosconi -> (Hospital Central) -> Punta de linea.";
	private String fifteen = "Enlace Rutas 36 Y A005 -> Alacalufes -> 17 De Octubre -> José Cardarelli -> Roberto Payro -> Perú -> Eduardo Jenner -> Mozart -> Paúl Harris -> Paraguay -> Av. M.T. De Alvear -> (Puente Carretero) Av. España -> Mugnaini -> Cabrera -> Echeverria -> Pringles -> H.Irigoyen -> 25 De Mayo -> Sobremonte -> G.Alonso -> Av.España -> (Puente Carretero) -> Av.M.T. De Alvear -> Garibaldi -> José Verdi -> Paraguay -> Paúl Harris -> Mozart -> Eduardo Jenner -> Perú -> Roberto Payiro -> José Cardarelli -> 17 De Octubre -> Alacalufes -> Enlace Rutas 36 Y A005 -> E.De S. Wittouck -> Punta de linea.";
	private String sixteen = "RUTA Nº36 U.N.R.C. -> Reforma Universitaria -> Iguazu -> Quena, Nuevo Puente B.Alberdi -> Carlos Pelegrini -> Luis Pasteur L.N. alem -> Entre Rios -> Manuelita rosas -> Rafael Obligado -> J.V. Gonzalez -> Pte Peron -> Malavia -> Rafael Obligado -> Montevideo ->Int. Daguerre -> Liniers -> Pedro Goyena -> Ameguino  -> Paso -> Corrientes -> Newberi -> Caceros -> Nuevo Puente B.Alberdi -> R. Dario -> Chile -> Iguazu -> Reforma Universitaria -> RUTAS Nº 36  -> (U.N.R.C.) -> Punta de linea. ";
	private String seventeen = "U.N.R.C. -> Calle Publica 1 - Calle Publica 2 -> Calle Publica 3 - Monteagudo -> Reforma Universitaria -> Quirico Porreca -> Mariquita Sánchez De Thompson -> Colombia - Muñiz ->(Puente Juan Filloy) -> Jaime Gil - Alberdi -> V. Sarsfield -> Alvear -> Mugnanini -> Terminal -> Piedra Blanca -> Marconi -> Mignanini -> San Juan ->Constitución -> San Martín ->(Plaza Roca) Sobremonte -> Jaime Gil -> Rotonda -> Puente Juan Filloy -> Muñiz -> Quirico Porreca -> Ref. Universitaria -> Monteagudo -> Calle Publica 3 -> Calle Publica 2 -> Calle Publica 1 -> U.N.R.C. -> Punta de linea. ";
	private String eighteen = "Dinkeldein -> Fitz Roy -> Estrada -> Perito Moreno -> Dinkeldein -> Guardias Nacionales -> Dr. Carlos Rodríguez -> Pedro Zanni -> Piedra Blanca -> G. Marconi -> San Juan -> Constitución -> San Martín -> Plaza Roca -> Sobremonte -> Alonzo -> Alvear -> H. Mugnaini -> Dadone -> Piedra Blanca -> Pedro Zanni -> Dr. Carlos Rodríguez -> Guardias Nacionales -> Dinkeldein -> Punta de linea. ";
	private String a = "por el momento no disponibles";
	private String holmberg = "por el momento no disponibles";

	private String oneRedAditional = "Cementerio-Villa Golf / Barrio Alberdi Por Pedro Goyena";
	private String oneGreenAditional = "Cementerio-Villa Dalcar / Barrio Alberdi Por Luis Pasteur";
	private String twoRedAditional = "Barrio Las Ferias / Universidad Nacional";
	private String twoAditional = "Hospital Central / Universidad Nacional";
	private String threeAditional = "Centro De Salud / Barrio San Martín";
	private String fourAditional = "Hospital Central Hospital / San Antonio De Padua";
	private String fiveAditional = "B. Hipódromo - B. Bimaco / Puente J.Filloy - U.N.R.C";
	private String sixAditional = "H.Central - Barrio I.P.V. / U.N.R.C.- B. El Acordeón; Punta De Línea – (Hospital Central) ";
	private String sevenAditional = "Barrio Casasnova / H. San Antonio De Padua; Barrio Bimaco / B. San José De Calasanz";
	private String eightGreenAditional = "Villa Dalcar - V.Golf Club / U.N.R.C. - Palestro";
	private String eightRedAditional = "V. Golf Club -Villa Dalcar / Palestro - U.N.R.C.";
	private String nineGreenAditional = "B. I.P.V. - B.Fenix / Universidad Nacional; Barrio Alberdi / Mercado De Abasto";
	private String nineRedAditional = "I.P.V. - B.Fenix / Universidad Nacional; Barrio Alberdi / Mercado De Abasto";
	private String tenAditional = "Barrio Yapeyu / Barrio Casasnovas";
	private String elevenAditional = "420 Viviendas / Barrio Yapeyu.";
	private String twelveAditional = "E.De Servicio Wittouck / Gomar-Fideera Centro";
	private String thirteenAditional = "Universidad Nacional / B.Bimaco -Roque Sáenz Peña";
	private String fourteenAditional = "Hospital Central / Plaza San Martín";
	private String fifteenAditional = "E.De Servicio Wittouck / Centro De Salud; Enlace Rutas 36 Y A005 / Instituto Medico";
	private String sixteenAditional = "Universidad Nacional / Barrio Alberdi";
	private String seventeenAditional = "Universidad Nacional / Terminal De Ómnibus";
	private String eighteenAditional = "Nuevo Hospital / Plaza Roca";
	private String aAditional = "por el momento no disponibles";
	private String holmbergAditional = "por el momento no disponibles";

	private String[] paths = { oneRed, oneGreen, twoRed, two, three, four,
			five, six, seven, eightRed, eightGreen, nineRed, nineGreen, ten,
			eleven, twelve, thirteen, fourteen, fifteen, sixteen, seventeen,
			eighteen, a, holmberg };

	private String[] aditionalPaths = { oneRedAditional, oneGreenAditional,
			twoRedAditional, twoAditional, threeAditional, fourAditional,
			fiveAditional, sixAditional, sevenAditional, eightRedAditional,
			eightGreenAditional, nineRedAditional, nineGreenAditional,

			tenAditional, elevenAditional, twelveAditional, thirteenAditional,
			fourteenAditional, fifteenAditional, sixteenAditional,
			seventeenAditional, eighteenAditional, aAditional,
			holmbergAditional };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.path_bus);
		args = getIntent().getBundleExtra(BusActivity.BUNDLE);
		numberOfLine = (TextView) findViewById(R.id.path_bus_number_line);
		pathOfLine = (TextView) findViewById(R.id.path_bus_path_of_bus);
		aditionalInfo = (TextView) findViewById(R.id.path_bus_aditional_information);
		position = args.getInt(BusActivity.LINE_BUS_POSITION);
		numberOfLine.setText(BusActivity.mNameLine[position]);
		pathOfLine.setText(paths[position]);
		aditionalInfo.setText(aditionalPaths[position]);
	}

	public void goToSchedule(View view) {
		Intent intent = new Intent(PathBus.this, BusSchedules.class);
		intent.putExtra(BusActivity.BUNDLE, args);
		startActivity(intent);
	}

}
