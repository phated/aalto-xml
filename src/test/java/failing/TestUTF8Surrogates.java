package failing;

import java.io.ByteArrayOutputStream;

import org.codehaus.stax2.XMLStreamWriter2;

import stax2.wstream.BaseWriterTest;

public class TestUTF8Surrogates extends BaseWriterTest
{
    public void testWithKappas() throws Exception
    {
        //loop to find exactly at which point entity encoding kicks in.
//        for (int j = 0; j < 1000; j++)
        final int j = 985;
        {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            XMLStreamWriter2 w = getNonRepairingWriter(bos, "UTF-8", true);
            final String namespace = "http://example.org";
            StringBuilder kappas = new StringBuilder();
            for (int i = 0; i < (2000 + j); i++) {
                kappas.append("𝜅");
            }
            w.writeStartElement("", "ex", namespace);
            w.writeCharacters(kappas.toString());
            w.writeEndElement();
            w.close();

            String act = bos.toString("UTF-8");
            String exp = "<ex>" + kappas + "</ex>";
            if (!exp.equals(act)) {
                fail("Iteration "+j+" failed; exp length "+exp.length()+" vs actual "+act.length());
            }
            else System.err.println("#"+j+" length: "+exp.length());
        }
    }
}
