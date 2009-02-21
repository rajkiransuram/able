package able.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class StandardFormatter extends Formatter {
	private static final int CLASS_LENGTH = 20;

  public String format(LogRecord record) {
    try {
        StringBuilder sb = new StringBuilder();

        String level = "UNKN";
        if (record.getLevel().equals(Level.WARNING)) {
            level = "WARN";
        } else if (record.getLevel().equals(Level.SEVERE)) {
            level = "SEVR";
        } else if (record.getLevel().equals(Level.INFO)) {
            level = "INFO";
        } else if (record.getLevel().equals(Level.FINE)) {
            level = "FINE";
        } else if (record.getLevel().equals(Level.FINEST)) {
            level = "FNST";
        } else if (record.getLevel().equals(Level.FINER)) {
            level = "FINR";
        } else if (record.getLevel().equals(Level.CONFIG)) {
            level = "CONF";
        } else if (record.getLevel().equals(Level.OFF)) {
            level = "OFF ";
        } else if (record.getLevel().equals(Level.ALL)) {
            level = "ALL ";
        }

        sb.append(level).append(" ");

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm:ss");
        sb.append(sdf.format(new Date(record.getMillis()))).append(" ");


        int before = sb.length();
        sb.append(getFormattedClassName(record.getLoggerName()));
        int after = sb.length();

        for (int i = (after - before); i <= CLASS_LENGTH - 1; i++) {
            sb.append(' ');
        }

        sb.append(" - ");
        if (record.getParameters() != null && record.getParameters().length > 0) {
            java.util.Formatter formatter = new java.util.Formatter(sb);
            formatter.format(record.getMessage(), record.getParameters());
            formatter.format("\n");
        } else {
            sb.append(record.getMessage()).append("\n");
        }

        if (record.getThrown() != null) {
            StringWriter sw = new StringWriter();
            record.getThrown().printStackTrace(new PrintWriter(sw));
            sb.append(sw.toString());
        }

        return sb.toString();
    } catch (Exception e) {
        e.printStackTrace();
        return record.getMessage();
    }
  }

	private static String getFormattedClassName(String className) {

		StringBuilder sb = new StringBuilder(CLASS_LENGTH);
		int before = sb.length();
		
		int classNameLength = className.length();
		if (classNameLength > CLASS_LENGTH) {
			
			// First append the first letters of the packages
			int index = className.indexOf(".", 0);
			if (index != -1) {
				sb.append(className.charAt(0));
				sb.append('.');
			}
			while (index != -1) {
				int newIndex = className.indexOf(".", index + 1);
				if (newIndex != -1) {
					sb.append(className.charAt(index + 1));
					sb.append('.');
				}
				index = newIndex;
			}
			// Now append the formatted class name
			String classSimpleName = className.substring(className.lastIndexOf('.') + 1);
			int rem = CLASS_LENGTH - (sb.length() - before);
			if (classSimpleName.length() > rem) {
				classSimpleName = getFormattedSimpleClassName(classSimpleName, rem);
			}
			sb.append(classSimpleName);
		} else {
			sb.append(className);
		}
		
		return sb.toString();
	}

	private static String getFormattedSimpleClassName(String classSimpleName, int formattedNameLength) {
		final int classSimpleNameLength = classSimpleName.length();
		// split the words from the class name
		List<String> wordsInClassName = new ArrayList<String>();
		List<Integer> wordLengthsInClassName = new ArrayList<Integer>();
		int start = 0;
		for(int i = 1; i < classSimpleNameLength; i++) {
			final char loopChar = classSimpleName.charAt(i);
			if (Character.isUpperCase(loopChar) || !Character.isLetter(loopChar)) {
				wordsInClassName.add(classSimpleName.substring(start, i));
				wordLengthsInClassName.add(i - start);
				start = i;
			}
		}
		wordsInClassName.add(classSimpleName.substring(start));
		wordLengthsInClassName.add(classSimpleNameLength - start);
		final int numberOfWordsInName = wordsInClassName.size();
		// Determine the length of shortened word, a value in [2, 4]
		final int shortenLength = Math.max(2, Math.min(4, formattedNameLength / numberOfWordsInName));
		// now keep adding only three characters until size is sufficient
		StringBuilder sb = new StringBuilder(formattedNameLength);
		int lengthProcessed = 0, charsRemaining = formattedNameLength;
		boolean keepShortening = true;
		for (int i = 0; i < numberOfWordsInName && sb.length() < formattedNameLength; i++) {
			String currentWord = wordsInClassName.get(i);
			int currentWordLength = wordLengthsInClassName.get(i);
			
			if (keepShortening && (classSimpleNameLength - lengthProcessed <= charsRemaining)) {
				keepShortening = false;
			} 
			
			String loopWordToAppend = null;
			if (keepShortening && currentWordLength > shortenLength) {
				if (i + 1 == numberOfWordsInName) {
					// the last word in the name
					loopWordToAppend = currentWord.substring(0, charsRemaining - 1);
				} else {
					int remainingWordsLength = classSimpleNameLength - currentWordLength - lengthProcessed;
					int substringLength = Math.max(shortenLength, charsRemaining - remainingWordsLength);
					loopWordToAppend = currentWord.substring(0, substringLength);
				}
			} else {
				loopWordToAppend = currentWord;
			}
			if (i + 1 != numberOfWordsInName && loopWordToAppend.length() >= charsRemaining) {
				// there are more words and so we need space for the tilda
				sb.append(loopWordToAppend.substring(0, charsRemaining - 1));
				break;
			}			
			sb.append(loopWordToAppend);
			charsRemaining -= loopWordToAppend.length();
			
			lengthProcessed += currentWordLength;
		}
		if (keepShortening) {
			sb.append('~');
		}
		return sb.toString();
	}
	
	/*
	 * TODO Just for testing purposes, REMOVE this method
	 */
	public static void main(String[] args) {
		String input = null, result = null;
		System.out.printf("%-42s  %-22s \n", "Input Class Name", "Result");
		System.out.printf("%-42s  %-22s \n", "----------------", "------");
		input = "SimpleDateFormat";
		result = getFormattedClassName(input);
		System.out.printf("%-42s  %-22s \n", input, result);
		input = "java.util.logging.LogRecord";
		result = getFormattedClassName(input);
		System.out.printf("%-42s  %-22s \n", input, result);
		input = "java.net.InetSocketAddress";
		result = getFormattedClassName(input);
		System.out.printf("%-42s  %-22s \n", input, result);
		input = "able.util.SocketTestWaitCondition";
		result = getFormattedClassName(input);
		System.out.printf("%-42s  %-22s \n", input, result);
		input = "org.test.logging.AnotherClassToBeTested";
		result = getFormattedClassName(input);
		System.out.printf("%-42s  %-22s \n", input, result);
		input = "org.test.AnotherClassWithLotsOfWordsInName";
		result = getFormattedClassName(input);
		System.out.printf("%-42s  %-22s \n", input, result);
		input = Character.UnicodeBlock.class.getName();
		result = getFormattedClassName(input);
		System.out.printf("%-42s  %-22s \n", input, result);
				
	}
}
