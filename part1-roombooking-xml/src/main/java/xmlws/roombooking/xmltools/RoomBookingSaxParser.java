package xmlws.roombooking.xmltools;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import xmlws.roombooking.xmltools.samples.RoomBookingBasicSaxParser;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RoomBookingSaxParser implements RoomBookingParser {

        private RoomBooking roomBooking = new RoomBooking();

        private String localNameTmp;
        private int nbFois;

        /**
         * Parse an xml file provided as an input stream
         *
         * @param inputStream the input stream corresponding to the xml file
         */
        public RoomBooking parse(InputStream inputStream) {
            try {
                SAXParserFactory spf = SAXParserFactory.newInstance();
                spf.setNamespaceAware(true);
                SAXParser saxParser = spf.newSAXParser();
                saxParser.parse(inputStream, new RoomBookingSaxParser.RoomBookingHandler());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return roomBooking;
        }

        private class RoomBookingHandler extends DefaultHandler {

            public void startElement(String namespaceURI,
                                     String localName,
                                     String qName,
                                     Attributes atts)
                    throws SAXException {

                localNameTmp = localName;
            }

            public void characters(char ch[], int start, int length)
                    throws SAXException {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                String data = new String(ch, start, length);

                if (data.charAt(0) != '\n') {

                    if (localNameTmp.equals("label")) {
                        roomBooking.setRoomLabel(data);
                    }

                    if (localNameTmp.equals("username")) {
                        roomBooking.setUsername(data);
                    }

                    if (localNameTmp.equals("startDate")) {
                        try {
                            roomBooking.setStartDate(sdf.parse(data));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    if (localNameTmp.equals("endDate")) {
                        try {
                            roomBooking.setEndDate(sdf.parse(data));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
}


