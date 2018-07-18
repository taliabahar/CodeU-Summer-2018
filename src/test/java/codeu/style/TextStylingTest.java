package codeu.style;

import codeu.style.TextStyling;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class TextStylingTest {
  /**
  * unit tests for TextStyling
  */
   @Test
   public void testBold() {
      String boldHTML = "<b>text</b>";
      String convertedBBCode = TextStyling.BBCodeToHTML( "[b]text[/b]" );
      assertEquals(boldHTML, convertedBBCode);
   }

   @Test
   public void testItalic() {
      String italicHTML = "<i>text</i>";
      String convertedBBCode = TextStyling.BBCodeToHTML( "[i]text[/i]" );
      assertEquals(italicHTML, convertedBBCode);
   }

   @Test
   public void testEmojis() {
      String emoji = "A 🐱, 🐶 and a 🐭 became friends ❤️.";
      String converted = TextStyling.emojifyText("A &#128049;, &#x1f436; and a :mouse: became friends ❤️." );
      assertEquals(emoji, converted);
   }
}
