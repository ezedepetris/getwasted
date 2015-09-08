package training.util;

public class ProcessingString {

	private String link;

	/**
	 * @param data
	 *            {@link String} request.getDescriptionCurrentSubject()
	 * @param year
	 *            {@link String} request.getYearSelected()
	 * @return {@link String} La descipcion a mostrar de una materia.
	 * 
	 */
	public String getDescriptionOfSubject(String data, String year) {
		String code = data.substring(0, data.indexOf(" ,"));
		year = year.substring(0, year.indexOf(" ")) + ":"
				+ year.substring(year.indexOf(" "));
		String aux1 = data.substring(data.indexOf(" ,") + 1);
		aux1 = aux1.substring(aux1.indexOf(" ,") + 1);
		String quarter = aux1.substring(1, aux1.indexOf(" ,"));
		link = aux1.substring(aux1.lastIndexOf(" ,") + 2);
		String description = "Cod:" + code + "  " + year + "\nCuatrimestre:"
				+ quarter;
		return description;
	}

	/**
	 * @param info
	 *            {@link String} request.getDescriptionCorrelativeSelected(
	 *            nameCorrelativeSelected)
	 * @return {@link String} La descripcion de la materia correlativa
	 *         seleccionada.
	 */
	public String getDescriptionCorrelative(String info) {
		String code = info.substring(0, info.indexOf(" ,"));
		String aux1 = info.substring(info.indexOf(" ,") + 1);
		aux1 = aux1.substring(aux1.indexOf(" ,") + 1);
		String quarter = aux1.substring(1, aux1.indexOf(" ,"));
		String year = aux1.substring(aux1.indexOf(" ,") + 1);
		year = year.substring(1, year.indexOf(" ,"));
		year = "Año: " + year;
		String condition = aux1.substring(aux1.lastIndexOf(" ,") + 2);
		String description = "Cod: " + code + "  " + year + "\nCuatrimestre: "
				+ quarter + "\nCondición: " + condition;
		return description;
	}

	public String getLink() {
		return link;
	}
}
