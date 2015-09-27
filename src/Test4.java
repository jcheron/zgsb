import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test4 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println(getReponse("<reponse>Réponse \nAndroïd </reponse>blabla<script type=\"text/javascript\">"));

	}

	public static String getReponse(String input) {
		String result = "";
		Pattern p = Pattern.compile(Pattern.quote("<reponse>") + "((\\s|.)*)" + Pattern.quote("</reponse>"), Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(input);
		if (m.find()) {
			result = m.group(1);
		}
		return result;
	}
}
