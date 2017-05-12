import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class PasswordStrength {
	public enum Score //Enum with the given score
	{
		blank(0),
		poor(1),
		weak(2),
		medium(3),
		strong(4),
		very_strong(5);
		private int value;
		private Score(int value)
		{
			this.value = value; 
		}
		public int Value(Score score)
		{
			return this.value; 
		}
	}	

	public static int CheckPassword(String password) 
	{
			/*This function checks the password and gives it a proper score based on the following characteristics:
				1. Does it have capital letters?
				2. Does it have numbers?
				3. Does it have special characters?
			Most modern programs have these password checkers built in to prevent easy hacking of the users. 
			*/
		//Regular expressions for capital letters, numbers, or special characters
	    String pattern2 = "[A-Z]";
	    String pattern3 = "[1-9]";
	    String pattern4 = "[!@#$%^&*~?]";
		int score = 0;
		if(password.length() < 1)
			return 0;
		if(password.length() < 4)
			return 1;
		if(password.length() >= 8)
			score++;
		if(password.length() >= 12)
			score++;
		Pattern p2 = Pattern.compile(pattern2);
		Matcher match_capitals = p2.matcher(password);
		Pattern p3 = Pattern.compile(pattern3);
		Matcher match_numbers = p3.matcher(password);
		Pattern p4 = Pattern.compile(pattern4);
		Matcher match_specials = p4.matcher(password);
		//Uses the regular expressions to find out if the given expression contains specials/numbers/capital
		if(match_capitals.find())
		{
			score++;
			if(match_numbers.find())
			{
				score ++;
				if(match_specials.find())
				{
						score++;
				}
			}

		}

		
		if(score > 5) score = 5;
		switch(score)
		{
			case 0:
				return 0;
			case 1:
				return 1;
			case 2:
				return 2;
			case 3:
				return 3;
			case 4:
				return 4;
			case 5: 
				return 5;
		}
		return 0;
	}
}
