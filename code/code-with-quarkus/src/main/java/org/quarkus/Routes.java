package org.quarkus;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.http.entity.ContentType;
//import org.quarkus.repo.StateRepo;
import org.apache.camel.component.jackson.JacksonDataFormat;
import java.util.ArrayList;
//import org.quarkus.entity.RecordTable;
import org.apache.camel.component.jackson.ListJacksonDataFormat;


@ApplicationScoped
public class Routes extends RouteBuilder {

     @Inject
     GreetingResource greetingResource;

    // @Inject
    // TrackRecords trackRecords;
    @Override
    public void configure() throws Exception {

        onException(Exception.class)
            .handled(false)
            .log(LoggingLevel.ERROR,"${exception.message}")
            .log("${exception}");

        // String query = "authMethod=Basic&authPassword=redhat&authUsername=elastic";
        // produces messages to kafka

        // kafka consumer
        // rest().get("/kafka").to("direct:kafka");

        // from("direct:kafka")
        
        // from("kafka:{{kafka.topic.name0}}?brokers=localhost:9092&autoOffsetReset=earliest&groupId=Cons_OPS")
        //         .routeId("FromKafkareg")
        //         .log("Received : \"${headers}\"")
        //         .log("Received : \"${body}\"")
        //         .unmarshal().json()
        //         .process(greetingResource)
        //         .setHeader("application_id",simple("${body.getApplication_id}"))
        //         .log("op : ${header.op}")
        //         .marshal().json()
        //         .log("completed here ${body}")
        //         .setHeader(Exchange.HTTP_METHOD,constant("POST"))
        //         .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
        //         .choice().when(simple("${header.op} == 'u'"))
        //         .log("chstart")
        //         .setBody(simple("{\"doc\":${body}}"))
        //         .toD("http://{{host.port.elastic}}/stud_2122/_update/${headers.application_id}")
        //         .log("chend")
        //         .endChoice()
        //         .otherwise()
        //         .log("otherstart")
        //         .toD("http://{{host.port.elastic}}/stud_2122/_doc/${headers.application_id}")
        //         // .log("Inserting data in db ${headers.application_id}")
        //         // .toD("sql:insert into camel_temp_records (application_id, registration) values ('${headers.application_id}', 'true')")
        //         // .log("insert end ${headers}")
        //         .log("otherend")
        //         .end();




                from("kafka:{{kafka.topic.name4}}?brokers=localhost:9092&autoOffsetReset=earliest&groupId=Cons_OPStest1")
                .routeId("FromKafkareg1")
                .log("Received : \"${headers}\"")
                .log("Received : \"${body}\"")
                .unmarshal().json()
                .process(greetingResource)
                .setHeader("application_id",simple("${body.getApplication_id}"))
                .log("op : ${header.op}")
                .marshal().json()
                .log("completed here ${body}")
                .setHeader(Exchange.HTTP_METHOD,constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
                .choice().when(simple("${header.op} == 'u'"))
                .log("chstart")
                .setBody(simple("{\"doc\":${body}}"))
                .toD("http://{{host.port.elastic}}/stud_2122/_update/${headers.application_id}")
                .log("chend")
                .endChoice()
                .otherwise()
                .log("otherstart")
                .toD("http://{{host.port.elastic}}/stud_2122/_doc/${headers.application_id}");



from("kafka:{{kafka.topic.name0}}?brokers=localhost:9092&autoOffsetReset=earliest&groupId=Cons_OPStest1")
                .routeId("ProcessBeneficiary")
                .log("Received : \"${headers}\"")
                .log("Received : \"${body}\"")
                .unmarshal().json()
                .process(greetingResource)
                .log("ooii ${body.getApplication_id}")
                .setHeader("application_id",simple("${body.getApplication_id}"))
                .marshal().json()
                .log("completed here ${body}")
                .setProperty("Body",simple("${body}") )
                .doTry()
                .setHeader(Exchange.HTTP_METHOD,constant("GET"))
                .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
                .toD("http://{{host.port.elastic}}/stud_2122/_doc/${headers.application_id}")
                .log("record was already present in main index")
                .setHeader(Exchange.HTTP_METHOD,constant("POST"))
                .setBody(simple("{\"doc\":${exchangeProperty.Body}}"))
                .log("Final : ${body}")
                .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
                .toD("http://{{host.port.elastic}}/stud_2122/_update/${headers.application_id}")
                // .log("Inserting data scheme in db ${headers.application_id}")
                //         .toD("sql:update camel_temp_records set scheme = 'true' where application_id = '${headers.application_id}'")
                        // .log("insert end ${headers}")
                .doCatch(HttpOperationFailedException.class)
                // .log("record was not present in main index")
                // .setHeader(Exchange.HTTP_METHOD,constant("POST"))
                .setBody(simple("${exchangeProperty.Body}"))
                .log("Final : ${body}")
                // .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
                // .toD("http://{{host.port.elastic}}/temp_scheme_data/_doc/${headers.application_id}")
                .setBody(simple("${body}"))
                .setHeader("JMSCorrelationID", simple("${headers.application_id}_processbeneficary"))
                .setHeader("Counter", constant("0"))
                .to("activemq:queue:temp.missing_Queue1")
                .log("created temp record")
                .end()
                .end()
                ;



from("kafka:{{kafka.topic.name5}}?brokers=localhost:9092&autoOffsetReset=earliest&groupId=Cons_OPStest1")
                .routeId("MeritApplicants")
                .log("Received : \"${headers}\"")
                .log("Received : \"${body}\"")
                .unmarshal().json()
                .process(greetingResource)
                .log("ooii ${body.getApplication_id}")
                .setHeader("application_id",simple("${body.getApplication_id}"))
                .marshal().json()
                .log("completed here ${body}")
                .setProperty("Body",simple("${body}") )
                .doTry()
                .setHeader(Exchange.HTTP_METHOD,constant("GET"))
                .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
                .toD("http://{{host.port.elastic}}/stud_2122/_doc/${headers.application_id}")
                .log("record was already present in main index")
                .setHeader(Exchange.HTTP_METHOD,constant("POST"))
                .setBody(simple("{\"doc\":${exchangeProperty.Body}}"))
                .log("Final : ${body}")
                .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
                .toD("http://{{host.port.elastic}}/stud_2122/_update/${headers.application_id}")
                // .log("Inserting data scheme in db ${headers.application_id}")
                //         .toD("sql:update camel_temp_records set scheme = 'true' where application_id = '${headers.application_id}'")
                        // .log("insert end ${headers}")
                .doCatch(HttpOperationFailedException.class)
                // .log("record was not present in main index")
                // .setHeader(Exchange.HTTP_METHOD,constant("POST"))
                .setBody(simple("${exchangeProperty.Body}"))
                .log("Final : ${body}")
                // .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
                // .toD("http://{{host.port.elastic}}/temp_scheme_data/_doc/${headers.application_id}")
                .setBody(simple("${body}"))
                .setHeader("JMSCorrelationID", simple("${headers.application_id}_merit"))
                .setHeader("Counter", constant("0"))
                .to("activemq:queue:temp.missing_Queue1")
                .log("created temp record")
                .end()
                .end()
                ;



                // // .log("Inserting data in db ${headers.application_id}")
                // // .toD("sql:insert into camel_temp_records (application_id, registration) values ('${headers.application_id}', 'true')")
                // // .log("insert end ${headers}")
                // .log("otherend")
                // .end();
                // temp_scheme
                // .doTry()
                //     .setHeader(Exchange.HTTP_METHOD,constant("GET"))
                //     .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
                //     .toD("http://{{host.port.elastic}}/temp_scheme_data/_doc/${headers.application_id}")
                    
                //     .log("scheme present in temp send to main index ${body}") 
                //     .unmarshal().json()
                //     .setBody(simple("${body[_source]}"))
                //     .log("i am here ${body} ")
                //     .marshal().json()
                //     .setBody(simple("{\"doc\":${body}}"))
                //     .setHeader(Exchange.HTTP_METHOD,constant("POST"))
                //     .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
                //         .toD("http://{{host.port.elastic}}/stud_2122/_update/${headers.application_id}")
                //         .log("scheme data transfer log response: ${headers.application_id} ${body}" )
                //         .log("scheme ${headers.application_id} data tranfered ")
                //         .log("Inserting data scheme in db ${headers.application_id}")
                //         .toD("sql:update camel_temp_records set scheme = 'true' where application_id = '${headers.application_id}'")
                //         .log("insert end ${headers}")
                //         .setHeader(Exchange.HTTP_METHOD,constant("DELETE"))
                //         .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
                //             .toD("http://{{host.port.elastic}}/temp_scheme_data/_doc/${headers.application_id}")
                //             .log("scheme Deleted successfully ${headers.application_id}")
                // .doCatch(HttpOperationFailedException.class)
                //     .log("Not found in any temp records ${headers.application_id}")
                //     .end()

                // // temp_warb
                // .doTry()
                //     .setHeader(Exchange.HTTP_METHOD,constant("GET"))
                //     .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
                //     .toD("http://{{host.port.elastic}}/temp_warb_data/_doc/${headers.application_id}")
                    
                //     .log("warb present in temp send to main index ${body} ${body.class} ") 
                //     .unmarshal().json()
                //     .setBody(simple("${body[_source]}"))
                //     .log("i am here ${body} ")
                //     .marshal().json()
                //     .setBody(simple("{\"doc\":${body}}"))
                //     .setHeader(Exchange.HTTP_METHOD,constant("POST"))
                //     .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
                //         .toD("http://{{host.port.elastic}}/stud_2122/_update/${headers.application_id}")
                //         .log("warb ${headers.application_id} data transfer log response: ${headers.application_id} ${body}" )
                //         .log("warb ${headers.application_id} data tranfered ")
                //         .log("Inserting data warb in db ${headers.application_id}")
                //         .toD("sql:update camel_temp_records set warb = 'true' where application_id = '${headers.application_id}'")
                //         .log("insert end ${headers}")
                //         .setHeader(Exchange.HTTP_METHOD,constant("DELETE"))
                //         .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
                //             .toD("http://{{host.port.elastic}}/temp_warb_data/_doc/${headers.application_id}")
                //             .log("Deleted successfully ${headers.application_id}")
                // .doCatch(HttpOperationFailedException.class)
                //     .log("Not found in any temp records ${headers.application_id}")
                //     .end()

                // // temp_verify
                // .doTry()
                //     .setHeader(Exchange.HTTP_METHOD,constant("GET"))
                //     .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
                //     .toD("http://{{host.port.elastic}}/temp_verify_data/_doc/${headers.application_id}")
                    
                //     .log("verify present in temp send to main index ${body} ${body.class} ") 
                //     .unmarshal().json()
                //     .setBody(simple("${body[_source]}"))
                //     .log("i am here ${body} ")
                //     .marshal().json()
                //     .setBody(simple("{\"doc\":${body}}"))
                //     .setHeader(Exchange.HTTP_METHOD,constant("POST"))
                //     .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
                //         .toD("http://{{host.port.elastic}}/stud_2122/_update/${headers.application_id}")
                //         .log("verify ${headers.application_id} data transfer log response: ${headers.application_id} ${body}" )
                //         .log("verify ${headers.application_id} data tranfered ${headers.application_id}")
                //         .log("Inserting data verification in db ${headers.application_id}")
                //         .toD("sql:update camel_temp_records set verification = 'true' where application_id = '${headers.application_id}'")
                //         .log("insert end ${headers}")
                //         .setHeader(Exchange.HTTP_METHOD,constant("DELETE"))
                //         .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
                //             .toD("http://{{host.port.elastic}}/temp_verify_data/_doc/${headers.application_id}")
                //             .log("Deleted successfully")
                // .doCatch(HttpOperationFailedException.class)
                //     .log("Not found in any temp records ${headers.application_id}")
                //     .end()    

                // // temp_qualification
                // .doTry()
                //     .setHeader(Exchange.HTTP_METHOD,constant("GET"))
                //     .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
                //     .toD("http://{{host.port.elastic}}/temp_qualification_data/_doc/${headers.application_id}")
                    
                //     .log("qualification present in temp send to main index ${body} ${body.class} ") 
                //     .unmarshal().json()
                //     .setBody(simple("${body[_source]}"))
                //     .log("i am here ${body} ")
                //     .marshal().json()
                //     .setBody(simple("{\"doc\":${body}}"))
                //     .setHeader(Exchange.HTTP_METHOD,constant("POST"))
                //     .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
                //         .toD("http://{{host.port.elastic}}/stud_2122/_update/${headers.application_id}")
                //         .log("Inserting data qualification in db ${headers.application_id}")
                //         .toD("sql:update camel_temp_records set qualification = 'true' where application_id = '${headers.application_id}'")
                //         .log("insert end ${headers}")
                //         .log("qualification ${headers.application_id} data transfer log response: ${headers.application_id} ${body}" )
                //         .log("qualification ${headers.application_id} data tranfered ${headers.application_id}")
                //         .setHeader(Exchange.HTTP_METHOD,constant("DELETE"))
                //         .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
                //             .toD("http://{{host.port.elastic}}/temp_qualification_data/_doc/${headers.application_id}")
                //             .log("Deleted successfully ${headers.application_id}")
                // .doCatch(HttpOperationFailedException.class)
                //     .log("Not found in any temp records")
                //     .end()



               
//         from("kafka:{{kafka.topic.name1}}?brokers=localhost:9092&autoOffsetReset=earliest&groupId=Cons_OPS")
//                 .routeId("FromKafkascheme")
//                 .log("Received : \"${headers}\"")
//                 .log("Received : \"${body}\"")
//                 .unmarshal().json()
//                 .process(greetingResource)
//                 .log("ooii ${body.getApplication_id}")
//                 .setHeader("application_id",simple("${body.getApplication_id}"))
//                 .marshal().json()
//                 .log("completed here ${body}")
//                 .setProperty("Body",simple("${body}") )
//                 .doTry()
//                 .setHeader(Exchange.HTTP_METHOD,constant("GET"))
//                 .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
//                 .toD("http://{{host.port.elastic}}/stud_2122/_doc/${headers.application_id}")
//                 .log("record was already present in main index")
//                 .setHeader(Exchange.HTTP_METHOD,constant("POST"))
//                 .setBody(simple("{\"doc\":${exchangeProperty.Body}}"))
//                 .log("Final : ${body}")
//                 .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
//                 .toD("http://{{host.port.elastic}}/stud_2122/_update/${headers.application_id}")
//                 // .log("Inserting data scheme in db ${headers.application_id}")
//                 //         .toD("sql:update camel_temp_records set scheme = 'true' where application_id = '${headers.application_id}'")
//                         // .log("insert end ${headers}")
//                 .doCatch(HttpOperationFailedException.class)
//                 // .log("record was not present in main index")
//                 // .setHeader(Exchange.HTTP_METHOD,constant("POST"))
//                 .setBody(simple("${exchangeProperty.Body}"))
//                 .log("Final : ${body}")
//                 // .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
//                 // .toD("http://{{host.port.elastic}}/temp_scheme_data/_doc/${headers.application_id}")
//                 .setBody(simple("${body}"))
//                 .setHeader("JMSCorrelationID", simple("${headers.application_id}_scheme"))
//                 .setHeader("Counter", constant("0"))
//                 .to("activemq:queue:temp.missing_Queue1")
//                 .log("created temp record")
//                 .end()
//                 .end()
//                 ;

//         from("kafka:{{kafka.topic.name2}}?brokers=localhost:9092&autoOffsetReset=earliest&groupId=Cons_OPS")
//                 .routeId("FromKafkaWarb")
//                 .log("Received : \"${headers}\"")
//                 .log("Received : \"${body}\"")
//                 .unmarshal().json()
//                 .process(greetingResource)
//                 .log("ooii ${body.getApplication_id}")
//                 .setHeader("application_id",simple("${body.getApplication_id}"))
//                 .marshal().json()
//                 .log("completed here ${body}")
//                 .setProperty("Body",simple("${body}") )
//                  //.delay(1000)
//                 .doTry()
//                 .setHeader(Exchange.HTTP_METHOD,constant("GET"))
//                 .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
//                 .toD("http://{{host.port.elastic}}/stud_2122/_doc/${headers.application_id}")
//                 .log("record was already present in main index")
//                 .setHeader(Exchange.HTTP_METHOD,constant("POST"))
//                 .setBody(simple("{\"doc\":${exchangeProperty.Body}}"))
//                 .log("Final : ${body}")
//                 .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
//                 .toD("http://{{host.port.elastic}}/stud_2122/_update/${headers.application_id}")
//                 .doCatch(HttpOperationFailedException.class)
//                 .setBody(simple("${exchangeProperty.Body}"))
//                 .log("Final : ${body}")
//                 .setBody(simple("${body}"))
//                 .setHeader("JMSCorrelationID", simple("${headers.application_id}_warb"))
//                 .setHeader("Counter", constant("0"))
//                 .to("activemq:queue:temp.missing_Queue1")
//                 .log("created temp record")
//                 .end()
//                 .end()
//                 ;


//             from("kafka:{{kafka.topic.name3}}?brokers=localhost:9092&autoOffsetReset=earliest&groupId=Cons_OPS")
//                 .routeId("FromKafkaVerify")
//                 .log("Received : \"${headers}\"")
//                 .log("Received : \"${body}\"")
//                 .unmarshal().json()
//                 .process(greetingResource)
//                 .log("ooii ${body.getApplication_id}")
//                 .setHeader("application_id",simple("${body.getApplication_id}"))
//                 .marshal().json()
//                 .log("completed here ${body}")
//                 .setProperty("Body",simple("${body}") )
//                 //.delay(500)              
//                 .doTry()
//                 .setHeader(Exchange.HTTP_METHOD,constant("GET"))
//                 .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
//                 .toD("http://{{host.port.elastic}}/stud_2122/_doc/${headers.application_id}")
//                 .log("record was already present in main index")
//                 .setHeader(Exchange.HTTP_METHOD,constant("POST"))
//                 .setBody(simple("{\"doc\":${exchangeProperty.Body}}"))
//                 .log("Final : ${body}")
//                 .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
//                 .toD("http://{{host.port.elastic}}/stud_2122/_update/${headers.application_id}")
//                 .doCatch(HttpOperationFailedException.class)
//                 .setBody(simple("${exchangeProperty.Body}"))
//                 .log("Final : ${body}")
//                 .setBody(simple("${body}"))
//                 .setHeader("JMSCorrelationID", simple("${headers.application_id}_verify"))
//                 .setHeader("Counter", constant("0"))
//                 .to("activemq:queue:temp.missing_Queue1")
//                 .log("created temp record")
//                 .end()
//                 .end()
//                 ;
                

//             from("kafka:{{kafka.topic.name4}}?brokers=localhost:9092&autoOffsetReset=earliest&groupId=Cons_OPS")
//                 .routeId("FromKafkaQualification")
//                 .log("Received : \"${headers}\"")
//                 .log("Received : \"${body}\"")
//                 .unmarshal().json()
//                 .process(greetingResource)
//                 .log("ooii ${body.getApplication_id}")
//                 .setHeader("application_id",simple("${body.getApplication_id}"))
//                 .marshal().json()
//                 .log("completed here ${body}")
//                 .setProperty("Body",simple("${body}") )
//                 //.delay(700)
//                .doTry()
//                 .setHeader(Exchange.HTTP_METHOD,constant("GET"))
//                 .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
//                 .toD("http://{{host.port.elastic}}/stud_2122/_doc/${headers.application_id}")
//                 .log("record was already present in main index")
//                 .setHeader(Exchange.HTTP_METHOD,constant("POST"))
//                 .setBody(simple("{\"doc\":${exchangeProperty.Body}}"))
//                 .log("Final : ${body}")
//                 .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
//                 .toD("http://{{host.port.elastic}}/stud_2122/_update/${headers.application_id}")
//                 .doCatch(HttpOperationFailedException.class)
//                 .setBody(simple("${exchangeProperty.Body}"))
//                 .log("Final : ${body}")
//                 .setBody(simple("${body}"))
//                 .setHeader("JMSCorrelationID", simple("${headers.application_id}_scheme"))
//                 .setHeader("Counter", constant("0"))
//                 .to("activemq:queue:temp.missing_Queue1")
//                 .log("created temp record")
//                 .end()
//                 .end();

        
// //             from("timer://foo?period=90000&synchronous=true")
// //             .to("sql:select * from camel_temp_records where scheme = false or warb = false or qualification = false  or verification = false")
// //             .log("result: ${body}")
// //             .marshal().json()

// //             // .unmarshal().json()
// //             // .marshal().json()
// //             .unmarshal(new ListJacksonDataFormat (RecordTable.class))
// //             // .unmarshal(getJacksonDataFormat(Camel_temp_records.class))
// //             .log("result2: ${body}")
// //             .process(trackRecords);

// //             // .setProperty("query",simple("${body}"))
// //             // 

//                 from("kafka:{{kafka.topic.name5}}?brokers=localhost:9092&autoOffsetReset=earliest&groupId=Cons_OPS")
//                 .routeId("FromKafkaregR")
//                 .log("Received : \"${headers}\"")
//                 .log("Received : \"${body}\"") 
//                 .unmarshal().json()
//                 .process(greetingResource)
//                 .setHeader("application_id",simple("${body.getApplication_id}"))
//                 .log("op : ${header.op}")
//                 .marshal().json()
//                 .log("completed here ${body}")
//                 .setHeader(Exchange.HTTP_METHOD,constant("POST"))
//                 .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
//                 .choice().when(simple("${header.op} == 'u'"))
//                 .log("chstart")
//                 .setBody(simple("{\"doc\":${body}}"))
//                 .toD("http://{{host.port.elastic}}/stud_2122/_update/${headers.application_id}")
//                 .log("chend")
//                 .endChoice()
//                 .otherwise()
//                 .log("otherstart")
//                 .toD("http://{{host.port.elastic}}/stud_2122/_doc/${headers.application_id}")
//                 // .log("Inserting data in db ${headers.application_id}")
//                 // .toD("sql:insert into camel_temp_records (application_id, registration) values ('${headers.application_id}', 'true')")
//                 // .log("insert end ${headers}")
//                 .log("otherend")
//                 .end();
               

               
//         from("kafka:{{kafka.topic.name6}}?brokers=localhost:9092&autoOffsetReset=earliest&groupId=Cons_OPS")
//                 .routeId("FromKafkaschemeR")
//                .log("Received : \"${headers}\"")
//                 .log("Received : \"${body}\"")
//                 .unmarshal().json()
//                 .process(greetingResource)
//                 .log("ooii ${body.getApplication_id}")
//                 .setHeader("application_id",simple("${body.getApplication_id}"))
//                 .marshal().json()
//                 .log("completed here ${body}")
//                 .setProperty("Body",simple("${body}") )
//                  //.delay(1000)
//                 .doTry()
//                 .setHeader(Exchange.HTTP_METHOD,constant("GET"))
//                 .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
//                 .toD("http://{{host.port.elastic}}/stud_2122/_doc/${headers.application_id}")
//                 .log("record was already present in main index")
//                 .setHeader(Exchange.HTTP_METHOD,constant("POST"))
//                 .setBody(simple("{\"doc\":${exchangeProperty.Body}}"))
//                 .log("Final : ${body}")
//                 .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
//                 .toD("http://{{host.port.elastic}}/stud_2122/_update/${headers.application_id}")
//                 .doCatch(HttpOperationFailedException.class)
//                 .setBody(simple("${exchangeProperty.Body}"))
//                 .log("Final : ${body}")
//                 .setBody(simple("${body}"))
//                 .setHeader("JMSCorrelationID", simple("${headers.application_id}_scheme"))
//                 .setHeader("Counter", constant("0"))
//                 .to("activemq:queue:temp.missing_Queue1")
//                 .log("created temp record")
//                 .end()
//                 .end()
//                 ;


//         from("kafka:{{kafka.topic.name7}}?brokers=localhost:9092&autoOffsetReset=earliest&groupId=Cons_OPS")
//                 .routeId("FromKafkaWarbR")
//                .log("Received : \"${headers}\"")
//                 .log("Received : \"${body}\"")
//                 .unmarshal().json()
//                 .process(greetingResource)
//                 .log("ooii ${body.getApplication_id}")
//                 .setHeader("application_id",simple("${body.getApplication_id}"))
//                 .marshal().json()
//                 .log("completed here ${body}")
//                 .setProperty("Body",simple("${body}") )
//                  //.delay(1000)
//                 .doTry()
//                 .setHeader(Exchange.HTTP_METHOD,constant("GET"))
//                 .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
//                 .toD("http://{{host.port.elastic}}/stud_2122/_doc/${headers.application_id}")
//                 .log("record was already present in main index")
//                 .setHeader(Exchange.HTTP_METHOD,constant("POST"))
//                 .setBody(simple("{\"doc\":${exchangeProperty.Body}}"))
//                 .log("Final : ${body}")
//                 .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
//                 .toD("http://{{host.port.elastic}}/stud_2122/_update/${headers.application_id}")
//                 .doCatch(HttpOperationFailedException.class)
//                 .setBody(simple("${exchangeProperty.Body}"))
//                 .log("Final : ${body}")
//                 .setBody(simple("${body}"))
//                 .setHeader("JMSCorrelationID", simple("${headers.application_id}_warb"))
//                 .setHeader("Counter", constant("0"))
//                 .to("activemq:queue:temp.missing_Queue1")
//                 .log("created temp record")
//                 .end()
//                 .end()
//                 ;




//             from("kafka:{{kafka.topic.name8}}?brokers=localhost:9092&autoOffsetReset=earliest&groupId=Cons_OPS")
//                 .routeId("FromKafkaVerifyR")
//                 .log("Received : \"${headers}\"")
//                 .log("Received : \"${body}\"")
//                 .unmarshal().json()
//                 .process(greetingResource)
//                 .log("ooii ${body.getApplication_id}")
//                 .setHeader("application_id",simple("${body.getApplication_id}"))
//                 .marshal().json()
//                 .log("completed here ${body}")
//                 .setProperty("Body",simple("${body}") )
//                  //.delay(1000)
//                 .doTry()
//                 .setHeader(Exchange.HTTP_METHOD,constant("GET"))
//                 .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
//                 .toD("http://{{host.port.elastic}}/stud_2122/_doc/${headers.application_id}")
//                 .log("record was already present in main index")
//                 .setHeader(Exchange.HTTP_METHOD,constant("POST"))
//                 .setBody(simple("{\"doc\":${exchangeProperty.Body}}"))
//                 .log("Final : ${body}")
//                 .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
//                 .toD("http://{{host.port.elastic}}/stud_2122/_update/${headers.application_id}")
//                 .doCatch(HttpOperationFailedException.class)
//                 .setBody(simple("${exchangeProperty.Body}"))
//                 .log("Final : ${body}")
//                 .setBody(simple("${body}"))
//                 .setHeader("JMSCorrelationID", simple("${headers.application_id}_verify"))
//                 .setHeader("Counter", constant("0"))
//                 .to("activemq:queue:temp.missing_Queue1")
//                 .log("created temp record")
//                 .end()
//                 .end()
//                 ;
                
                

//             from("kafka:{{kafka.topic.name9}}?brokers=localhost:9092&autoOffsetReset=earliest&groupId=Cons_OPS")
//                 .routeId("FromKafkaQualificationR")
//                 .log("Received : \"${headers}\"")
//                 .log("Received : \"${body}\"")
//                 .unmarshal().json()
//                 .process(greetingResource)
//                 .log("ooii ${body.getApplication_id}")
//                 .setHeader("application_id",simple("${body.getApplication_id}"))
//                 .marshal().json()
//                 .log("completed here ${body}")
//                 .setProperty("Body",simple("${body}") )
//                  //.delay(1000)
//                 .doTry()
//                 .setHeader(Exchange.HTTP_METHOD,constant("GET"))
//                 .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
//                 .toD("http://{{host.port.elastic}}/stud_2122/_doc/${headers.application_id}")
//                 .log("record was already present in main index")
//                 .setHeader(Exchange.HTTP_METHOD,constant("POST"))
//                 .setBody(simple("{\"doc\":${exchangeProperty.Body}}"))
//                 .log("Final : ${body}")
//                 .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
//                 .toD("http://{{host.port.elastic}}/stud_2122/_update/${headers.application_id}")
//                 .doCatch(HttpOperationFailedException.class)
//                 .setBody(simple("${exchangeProperty.Body}"))
//                 .log("Final : ${body}")
//                 .setBody(simple("${body}"))
//                 .setHeader("JMSCorrelationID", simple("${headers.application_id}_qualification"))
//                 .setHeader("Counter", constant("0"))
//                 .to("activemq:queue:temp.missing_Queue1")
//                 .log("created temp record")
//                 .end()
//                 .end()
//                 ;

// // from("timer:mytimer?period=500000")
// //         .setBody(constant("HELLO from Camel!"))
// //         .setHeader("JMSCorrelationID", constant("your-custom-correlation-id"))
// //         .setHeader("Counter", constant("1"))
// //         .to("activemq:queue:new.queue");

//   from("activemq:queue:temp.missing_Queue1")
//         .log("Received message missing_Queue1: ${header.JMSCorrelationID}")
//         .log("Recieved Counter missing_Queue1: ${header.Counter}")
//         .log("Body: ${body}")
//         .process(exchange-> {
//             String correlationId = exchange.getIn().getHeader("JMSCorrelationID", String.class);
//             String newst = correlationId.split("_")[0];
//             exchange.setProperty("newId", newst);
//         })
//         .unmarshal().json()
//         .marshal().json()
//         .setProperty("Body",simple("${body}") )
//         .doTry()
//         .setHeader(Exchange.HTTP_METHOD,constant("GET"))
//         .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
//         .toD("http://{{host.port.elastic}}/stud_2122/_doc/${exchangeProperty.newId}")
//         .log("record was already present in main index")
//         .setHeader(Exchange.HTTP_METHOD,constant("POST"))
//         .setBody(simple("{\"doc\":${exchangeProperty.Body}}"))
//         .setHeader(Exchange.CONTENT_TYPE,constant(ContentType.APPLICATION_JSON))
//         .toD("http://{{host.port.elastic}}/stud_2122/_update/${exchangeProperty.newId}")
//         .log("Inserting data to main Index ${exchangeProperty.newId}")
//         .doCatch(HttpOperationFailedException.class)
//                 .setBody(simple("${exchangeProperty.Body}"))
//                 .process( exchange->{
//                     String headerCounter = exchange.getIn().getHeader("Counter", String.class);
//                     int Countcheck =  Integer.valueOf(headerCounter);
//                     Countcheck += 1;
//                     headerCounter = String.valueOf(Countcheck);
//                     exchange.getIn().setHeader("Counter", headerCounter);
//                 })
//                 .setBody(simple("${body}"))
//                 .to("activemq:queue:temp.delayed_Queue1")
//                 .log("created expiry record ${exchangeProperty.newId} and ${header.Counter} ")
//             .end();
                        



//         from("timer:mytimer?period=10000&synchronous=true")
//         .log("started timer")
//         from("activemq:queue:temp.delayed_Queue1")
//         // .log("message from ${header.JMSCorrelationID}")
//         .log("Received message delayed queue: ${header.JMSCorrelationID}")
//         .choice().when(simple("${header.Counter} == '7'"))
//         .log("counter exceeded")
//         .to("activemq:queue:temp.EXPIRYQueue1")
//         .endChoice()
//         .otherwise()
//         .to("activemq:queue:temp.missing_Queue1")
//         .end()
//         .delay(5000);



    }

}
