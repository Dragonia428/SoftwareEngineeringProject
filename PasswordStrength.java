import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class PasswordStrength {
	
	public static void main(String[] args)
	{
		System.out.println(CheckPassword("HelloWorld123"));
	}
	enum Score 
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
	}	
	public static Score CheckPassword(String password)
	{
		String pattern1 = "[a-z]";
	    String pattern2 = "[A-Z]";
	    String pattern3 = "[1-9]";
		int score = 0;
		if(password.length() < 1)
			return Score.blank; 
		if(password.length() < 4)
			return Score.poor;
		if(password.length() >= 8)
			score++;
		if(password.length() >= 12)
			score++;
		Pattern p1 =  Pattern.compile(pattern1);
		Matcher match_letters = p1.matcher(password);
		Pattern p2 = Pattern.compile(pattern2);
		Matcher match_capitals = p2.matcher(password);
		Pattern p3 = Pattern.compile(pattern3);
		Matcher match_numbers = p3.matcher(password);
		if(match_letters.find())
		{
			score++;
			if(match_capitals.find())
			{
				score++;
				if(match_numbers.find())
				{
					score ++;
				}
			}

		}
		if(score > 5) score = 5;
		switch(score)
		{
			case 0:
				return Score.blank;
			case 1:
				return Score.poor;
			case 2:
				return Score.weak;
			case 3:
				return Score.medium;
			case 4:
				return Score.strong;
			case 5: 
				return Score.very_strong;
		}
		return Score.blank;
	}
}
