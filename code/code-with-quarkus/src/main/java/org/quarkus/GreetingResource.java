package org.quarkus;


import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
// import javax.persistence.EntityManager;
// import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RestClient;

// import org.quarkus.repo.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import io.opentelemetry.sdk.metrics.data.Data;
import io.quarkus.logging.Log;
import io.vertx.core.json.JsonObject;




// @Path("/hello")
@ApplicationScoped
public class GreetingResource implements Processor{


    // @Inject
    // RestClient restClient;

    // @ConfigProperty(name="query1")
    // String query1;

    // @ConfigProperty(name="query2")
    // String query2;

    // @ConfigProperty(name="query3")
    // String query3;

    // @ConfigProperty(name="query5")
    // String query5;

    // @Inject
    // repository repo;
  
  
  
    // @GET 
    // @Path("/test")
    
    // public String test (){
        
        
        
        

        // NewPojo newStud = new NewPojo();
        // public void dummystudent(){
            
        //     newStud.setApplicationId("1ABC");
        //     newStud.setStudentName("Pratyaksh");
        //     newStud.setStateId((long)2);
            
        //     System.out.println("ffff"+newStud.stateId);
        // }


        @ActivateRequestContext
        @Transactional


        public void process(Exchange exchange) {
            
            // System.out.println("yyyyyyyyyyyyyy"+exchange.getIn().getHeader("kafka.TOPIC"));

            // System.out.println("qqqqq " + exchange.getIn().getBody());
            HashMap map = (HashMap) exchange.getIn().getBody();
            HashMap after;

            if(map==null){
                return;
            }
            // else if(map.get("after")==null){
            //     after=(HashMap) map.get("before");
            //     // System.out.println("hehejeje " + map.get("before"));
            //     DataApplicationRegistration registration = new DataApplicationRegistration();
            //     registration.setApplication_id((String)after.get("application_id"));
            //     registration.setIs_deleted(true);
            //     exchange.getIn().setBody(registration);                
            //     return;
            // }
            else{
                after=(HashMap) map.get("after");
                String op = (String) map.get("op");
                exchange.getIn().setHeader("op", op);
                // System.out.println("hehejeje " + map.get("after"));

            }



             if ((exchange.getIn().getHeader("kafka.TOPIC").equals("NSP.fresh.payment.process_beneficary"))){

                System.out.println("Process beneficiary route started");
                ProcessBeneficiary processbeneficary = new ProcessBeneficiary(after);
                exchange.getIn().setBody(processbeneficary);
             }

             if ((exchange.getIn().getHeader("kafka.TOPIC").equals("NSP.fresh.nspprod.data_applicant_registration_details"))){

                System.out.println(" data_applicant_registration_details route started");
                RegistrationDetails registrationdetails = new RegistrationDetails(after);
                exchange.getIn().setBody(registrationdetails);
             }


             if ((exchange.getIn().getHeader("kafka.TOPIC").equals("NSP.fresh.payment.in_merit_applicants"))){

                System.out.println(" data_applicant_registration_details route started");
                MeritApplicants meritapplicants = new MeritApplicants(after);
                exchange.getIn().setBody(meritapplicants);
             }



            
            

//     if ((exchange.getIn().getHeader("kafka.TOPIC").equals("NSP.fresh.nspprod.data_applicant_registration_details")) || exchange.getIn().getHeader("kafka.TOPIC").equals("NSP.renewal.nspprod.data_applicant_registration_details") ){

//         System.out.println("registrationnnn");

//         DataApplicationRegistration registration = new DataApplicationRegistration(after);

         
//             if (after.get("domicile_state_id") != null){
//                 Mst_states state = new Mst_states();
//                 state = stateRepo.findById(Long.valueOf((String)after.get("domicile_state_id")));
//                 registration.setPfms_state_code((BigInteger)state.getPfms_state_code());
//                 registration.setState_name((String)state.getState_name());
//                 registration.setShort_name((String) state.getShort_name());
//                 registration.setState_ut((String) state.getState_ut());
//                 Log.info("mst_states ---");
            
//             }
//             // if (((Integer)after.get("disablity_type")) != 0 && after.get("disablity_type") != null){
//             //     Mst_disability_type disabl = new Mst_disability_type();
//             //     disabl = disability.findById(Long.valueOf(((Integer)after.get("disablity_type")).toString()));
//             //     registration.setDisability_desc((String)disabl.getDisability_desc());
            
//             // }

//             if (after.get("permanent_district_id") != null){
//                 Mst_districts dst  = new Mst_districts();
//                 dst = dist.findById(Long.valueOf((after.get("permanent_district_id")).toString()));
//                 registration.setDistrict_name((String)dst.getDistrict_name());
//                 Log.info("mst_district ---");
                
//             }

//             if (after.get("religion")!=null){
//                 Mst_religion rel = new Mst_religion();
//                 rel = relegion.findById(Long.valueOf(((Integer)(after.get("religion"))).toString()));
//                 registration.setReligion_name((String)rel.getReligion_name());
//                 Log.info("mst_rel ---");

//             }

//             if (after.get("marital_status")!=null){
//                 Mst_marital_status mar = new Mst_marital_status();
//                 mar = marital.findById(Long.valueOf(((Integer)(after.get("marital_status"))).toString()));
//                 registration.setMarital_status_name(mar.getMarital_status_name());
//             }

//             // if (after.get("gender")!=null){
//             //     Mst_gender gen = new Mst_gender();
//             //     gen = gender.findById(Long.valueOf((String)after.get("gender")));
//             //     registration.setGender(gen.getGender_name());
//             // }
            
//             // if (after.get("application_status")!=null){
//             //     Mst_application_status aplstatus = new Mst_application_status();
//             //     aplstatus = apst.findById(Long.valueOf((String)after.get("application_status")));
//             //     registration.setDescription(aplstatus.getDescription());
//             // }

//             if (after.get("category_id")!=null){
//                 Mst_category cat= new Mst_category();
//                 cat =  catgry.findById(Long.valueOf((String)after.get("category_id")));
//                 registration.setCategory_name(cat.getCategory_name());
//             }

//             if (after.get("modeofstudy")!=null){
//                 Mst_course_type ctype = new Mst_course_type();
//                 ctype = coursetype.findById(Long.valueOf(((Integer)(after.get("modeofstudy"))).toString()));
//                 registration.setCourse_type(ctype.getCourse_type());
//             }

//             if (after.get("parent_occupation")!=null){
//                 Mst_parental_occupation prntoccup = new Mst_parental_occupation();
//                 prntoccup = occup.findById(Long.valueOf(((Integer)(after.get("parent_occupation"))).toString()));
//                 registration.setOccup_name(prntoccup.getOccup_name());
//             }

//             exchange.getIn().setBody(registration);

// }    



// //qualification

// if ((exchange.getIn().getHeader("kafka.TOPIC").equals("NSP.fresh.nspprod.data_applicant_qualifications")) || exchange.getIn().getHeader("kafka.TOPIC").equals("NSP.renewal.nspprod.data_applicant_qualifications")){

//             System.out.println("qualification");

//             DataApplicantQualifications qualification = new DataApplicantQualifications(after);

    

//         if((!String.valueOf(after.get("c_university_id")).equals("0")) && after.get("c_university_id")!=null){
//             Mst_university mst_univ_c = new Mst_university();
//             mst_univ_c = mST_UNIVERSITYrepo.findById(Long.valueOf((String)after.get("c_university_id")));
//             qualification.setCurrent_university_name(mst_univ_c.getUniversity_name());
//         } 

//         // if(after.get("p_university_id")!="0")
//         if((!String.valueOf(after.get("p_university_id")).equals("0")) && after.get("p_university_id")!=null){
//             Mst_university mst_univ_p = new Mst_university();
//             mst_univ_p = mST_UNIVERSITYrepo.findById(Long.valueOf((String)after.get("p_university_id")));
//             qualification.setPrevious_university_name(mst_univ_p.getUniversity_name());
//         } 

//         // if(after.get("x_university_id")!=null)
//         if((!String.valueOf(after.get("x_university_id")).equals("0")) && after.get("x_university_id")!=null){
//             Mst_university mst_univ_x = new Mst_university();
//             mst_univ_x = mST_UNIVERSITYrepo.findById(Long.valueOf((String)after.get("x_university_id")));
//             qualification.setPrevious_university_name(mst_univ_x.getUniversity_name());
//         } 

//         if((!String.valueOf(after.get("xii_university_id")).equals("0")) && (!String.valueOf(after.get("xii_university_id")).equals("-1")) && after.get("xii_university_id")!=null ){
//                     Mst_university mst_univ_xii = new Mst_university();
//             mst_univ_xii = mST_UNIVERSITYrepo.findById(Long.valueOf((String)after.get("xii_university_id")));
//             qualification.setPrevious_university_name(mst_univ_xii.getUniversity_name());
//         } 

//         if((!String.valueOf(after.get("x_institution_id")).equals("0")) && after.get("x_institution_id")!=null){
//             Mst_institution mst_institution_x=new Mst_institution();
//             mst_institution_x=mST_INSTITUTIONrepo.findById(Long.valueOf((String)after.get("x_institution_id")));
//             qualification.setX_institution(mst_institution_x.getInstitution_name());

//         }

//         if((!String.valueOf(after.get("xii_institution_id")).equals("0")) && after.get("xii_institution_id")!=null){
//             Mst_institution mst_institution_xii=new Mst_institution();
//             mst_institution_xii=mST_INSTITUTIONrepo.findById(Long.valueOf((String)after.get("xii_institution_id")));
//             qualification.setXii_institution(mst_institution_xii.getInstitution_name());

//         }

//         if (after.get("xii_course_id")!=null){
//             Mst_course course_xii = new Mst_course();
//             course_xii = mST_COURSErepo.findById(Long.valueOf((after.get("xii_course_id")).toString()));
//             qualification.setXii_course_name(course_xii.getCourse_name());
//         }

//         // if (after.get("x_course_id")!=null){
//         //     Mst_course course_x = new Mst_course();
//         //     course_x = mST_COURSErepo.findById(Long.valueOf((String)after.get("x_course_id")));
//         //     qualification.setX_course_name(course_x.getCourse_name());
//         // }

//         if((!String.valueOf(after.get("c_course_id")).equals("0")) && after.get("c_course_id")!=null){
//             Mst_course course_c = new Mst_course();
//             course_c = mST_COURSErepo.findById(Long.valueOf((String)after.get("c_course_id")));
//             qualification.setCurrent_course_name(course_c.getCourse_name());
//             qualification.setCurrent_course_duration(course_c.getDuration());
//         }

//         if((!String.valueOf(after.get("p_course_id")).equals("0")) && after.get("p_course_id")!=null){
//             Mst_course course_p = new Mst_course();
//             course_p = mST_COURSErepo.findById(Long.valueOf((String)after.get("p_course_id")));
//             qualification.setPrevious_course_name(course_p.getCourse_name());
//         }

//         if((!String.valueOf(after.get("c_institution_id")).equals("0")) && after.get("c_institution_id")!=null){
//             Mst_institution mst_institution=new Mst_institution();
//             mst_institution=mST_INSTITUTIONrepo.findById(Long.valueOf((String)after.get("c_institution_id")));
//             qualification.setCurrent_inst_district_id(mst_institution.getDistrict_id());
//             qualification.setCurrent_inst_id(new BigDecimal(mst_institution.getInstitution_id()));
//             qualification.setCurrent_inst_name(mst_institution.getInstitution_name());
//             qualification.setCurrent_inst_final_aishe_code(mst_institution.getFinal_aishe_code());
//             qualification.setCurrent_inst_university_id(mst_institution.getUniversity_id());
//             qualification.setCurrent_inst_pin_code(mst_institution.getPin_code());
//             qualification.setCurrent_inst_state_id(mst_institution.getState_id());
//             qualification.setCurrent_inst_email_id(mst_institution.getEmail_id());
//             qualification.setCurrent_inst_delete_flag(mst_institution.delete_flag);
//             qualification.setCurrent_inst_phone_no(mst_institution.getPhone_no());
//             qualification.setCurrent_inst_contact_person_name(mst_institution.getContact_person_name());
//             qualification.setCurrent_inst_contact_person_mobile(mst_institution.getContact_person_mobile());
//             qualification.setCurrent_inst_contact_person_phone(mst_institution.getContact_person_phone());
//             qualification.setCurrent_inst_postal_address_department(mst_institution.getPostal_address_department());
//             qualification.setCurrent_inst_postal_address_street(mst_institution.getPostal_address_street());
//             qualification.setCurrent_inst_postal_address_building(mst_institution.getPostal_address_building());
//             qualification.setCurrent_inst_address_line1(mst_institution.getInst_address_line1());
//             qualification.setCurrent_inst_address_line2(mst_institution.getInst_address_line2());
//             qualification.setCurrent_inst_address_town_code(mst_institution.getInst_address_town_code());
//             qualification.setCurrent_inst_address_town_name(mst_institution.getInst_address_town_name());
//             qualification.setCurrent_inst_is_contact_updated(mst_institution.getIs_contact_upDated());
//             qualification.setCurrent_inst_is_approved_district(mst_institution.getIs_approved_district());
//             qualification.setCurrent_inst_is_submit_form(mst_institution.getIs_submit_form());
//             qualification.setCurrent_inst_head_name(mst_institution.getInst_head_name());
//             qualification.setCurrent_inst_head_mobile(mst_institution.getInst_head_mobile());
//             qualification.setCurrent_inst_head_phone(mst_institution.getInst_head_phone());
//             qualification.setCurrent_inst_head_email(mst_institution.getInst_head_email());
//             qualification.setCurrent_inst_head_designation(mst_institution.getInst_head_designation());
//             qualification.setCurrent_inst_form_reference_no(mst_institution.getInst_form_reference_no());
//             qualification.setCurrent_inst_is_save_form(mst_institution.getIs_save_form());
        
//         }



//         exchange.getIn().setBody(qualification);



//     }

//         //     }


}




    // @GET
    // @Path("/test")
    // public List<State> getAllStates() {
    //     return stateRepo.listAll();
    // }

     // warb.setApplication_id((String) after.get("application_id"));
            // warb.setRank(after.get("rank")== null ? 0: (Integer) after.get("rank"));
            // warb.setCategory_id(after.get("category_id") == null ? 0 : (Integer) after.get("category_id"));
            // warb.setForce_no(after.get("force_no") == null ? 0 : (Integer) after.get("force_no"));
            // warb.setName_of_personnel(after.get("name_of_personnel") == null ? null : (String) after.get("name_of_personnel"));
            // warb.setCurrent_working_status_of_personnel(after.get("current_working_status_of_personnel") == null ? null : (String) after.get("current_working_status_of_personnel"));
            // warb.setUnit(after.get("unit") == null ? null : (String) after.get("unit"));
            // warb.setPpo_no(after.get("ppo_no") == null ? null : (String) after.get("ppo_no"));
            // warb.setForce_type_id(after.get("force_type_id") == null ? 0 : (Integer) after.get("force_type_id"));
            // warb.setZone(after.get("zone") == null ? 0 : (Integer) after.get("zone"));
            // warb.setRailway_division(after.get("railway_division") == null ? "" : (String) after.get("railway_division"));
            // warb.setRailway_rank(after.get("railway_rank") == null ? 0 : (Integer) after.get("railway_rank"));
            // warb.setLic_state_id(after.get("lic_state_id") == null ? BigInteger.ZERO : (BigInteger) after.get("lic_state_id"));
            // warb.setLic_policy_no(after.get("lic_policy_no") == null ? null : (String) after.get("lic_policy_no"));
            // warb.setLic_id(after.get("lic_id") == null ? null : (String) after.get("lic_id"));
            // warb.setTop_class_exam_name(after.get("top_class_exam_name") == null ? BigInteger.ZERO : (BigInteger) after.get("top_class_exam_name"));
            // warb.setTop_class_rank(after.get("top_class_rank") == null ? BigInteger.ZERO : (BigInteger) after.get("top_class_rank"));
            // warb.setTop_class_exam_passing_year(after.get("top_class_exam_passing_year") == null ? BigInteger.ZERO : (BigInteger) after.get("top_class_exam_passing_year"));
            // warb.setIsapplicableforaicte(after.get("isapplicableforaicte") == null ? BigInteger.ZERO : (BigInteger) after.get("isapplicableforaicte"));
            // warb.setAicte_x_percentage(after.get("aicte_x_percentage") == null ? "" : (String) after.get("aicte_x_percentage"));
            // warb.setAicte_xii_percentage(after.get("aicte_xii_percentage") == null ? "" : (String) after.get("aicte_xii_percentage"));
            
    

                // verifications.setApplication_id(after.get("application_id") == null ? null : String.valueOf(after.get("application_id")));
                // verifications.setApplicant_name_in_aadhaar(after.get("applicant_name_in_aadhaar") == null ? null : String.valueOf(after.get("applicant_name_in_aadhaar")));
                // verifications.setAadhaar_verif_status(after.get("aadhaar_verif_status") == null ? "" : (String) after.get("aadhaar_verif_status"));
                // verifications.setStudent_updated(after.get("student_updated") == null ? null : (Boolean) after.get("student_updated"));
                // verifications.setStudent_updation_date(after.get("student_updation_date") == null ? null : String.valueOf(after.get("student_updation_date")));
                // verifications.setAadhaar_updation_date(after.get("aadhaar_updation_date") == null ? null : String.valueOf(after.get("aadhaar_updation_date")));
                // verifications.setNpci_flag(after.get("npci_flag") == null ? "" : (String) after.get("npci_flag"));
                // verifications.setNpci_bank_name(after.get("npci_bank_name") == null ? null : String.valueOf(after.get("npci_bank_name")));
                // verifications.setNpci_aadhaar_no(after.get("npci_aadhaar_no") == null ? null : String.valueOf(after.get("npci_aadhaar_no")));
                // verifications.setPfms_flag(after.get("pfms_flag") == null ? "" : (String) after.get("pfms_flag"));
                // verifications.setPfms_ifsc_code(after.get("pfms_ifsc_code") == null ? null : String.valueOf(after.get("pfms_ifsc_code")));
                // verifications.setPfms_name_as_per_bank(after.get("pfms_name_as_per_bank") == null ? null : String.valueOf(after.get("pfms_name_as_per_bank")));
                // verifications.setPfms_student_corection(after.get("pfms_student_corection") == null ? null : (Boolean) after.get("pfms_student_corection"));
                // verifications.setPfms_student_correction_date(after.get("pfms_student_correction_date") == null ? null : String.valueOf(after.get("pfms_student_correction_date")));
                // verifications.setName_similarity(after.get("name_similarity") == null ? null : (BigDecimal) after.get("name_similarity"));
                // verifications.setPay_path_id(after.get("pay_path_id") == null ? 0 : (int) after.get("pay_path_id"));
                // verifications.setAadhaar_name_similarity(after.get("aadhaar_name_similarity") == null ? null : (BigDecimal) after.get("aadhaar_name_similarity"));
                // verifications.setAcc_lock(after.get("acc_lock") == null ? "" : (String) after.get("acc_lock"));
                // verifications.setAcc_counter(after.get("acc_counter") == null ? 0 : (int) after.get("acc_counter"));
                // verifications.setPfms_response(after.get("pfms_response") == null ? null : String.valueOf(after.get("pfms_response")));
                // verifications.setName_match_category(after.get("name_match_category") == null ? null : String.valueOf(after.get("name_match_category")));
                // verifications.setIs_student_notified(after.get("is_student_notified") == null ? null : (Boolean) after.get("is_student_notified"));
                // verifications.setNotify_timestamp(after.get("notify_timestamp") == null ? null : String.valueOf(after.get("notify_timestamp")));
                // verifications.setSms_gw_response(after.get("sms_gw_response") == null ? null : String.valueOf(after.get("sms_gw_response")));
                // verifications.setAadhaar_authentication_method(after.get("aadhaar_authentication_method") == null ? 0 : (int) after.get("aadhaar_authentication_method"));
   

        // qualification.setIs_prev_class_promoted((Integer)after.get("is_prev_class_promoted"));
        // qualification.setC_university_id(new BigDecimal((String)after.get("c_university_id")));
        // qualification.setC_institution_id(new BigDecimal((String)after.get("c_institution_id")));
        // qualification.setC_course_id(new BigDecimal((String) after.get("c_course_id")));
        // qualification.setC_course_year((int) after.get("c_course_year"));
        // qualification.setP_university_id(new BigDecimal( (String)after.get("p_university_id")));
        // qualification.setP_institution_id(new BigDecimal((String)after.get("p_institution_id")));
        // qualification.setP_course_id(new BigDecimal((String)after.get("p_course_id")));
        // qualification.setP_course_year((int) after.get("p_course_year"));
        // qualification.setP_percentage(new BigDecimal( (String)after.get("p_percentage")));
        // qualification.setX_university_id(new BigDecimal((String)after.get("x_university_id")));
        // qualification.setX_institution_id(new BigDecimal((String)after.get("x_institution_id")));
        // qualification.setX_course_year((int) after.get("x_course_year"));
        // qualification.setX_percentage(new BigDecimal((String) after.get("x_percentage")));
        // qualification.setCompetitive_exam_score((int) after.get("competitive_exam_score"));
        // qualification.setCompetitive_exam_year((int)after.get("competitive_exam_year"));
        // qualification.setCompetitive_exam_state((int)after.get("competitive_exam_state"));
        // qualification.setRank_holder_passing_yr((int)after.get("rank_holder_passing_yr"));
        // qualification.setRank_holder_subject_id((int)after.get("rank_holder_subject_id"));
        // qualification.setAcademic_year((int) after.get("academic_year"));
        // qualification.set_application_discontinued((boolean)after.get("is_application_discontinued"));
        // qualification.setXii_course_id((int) after.get("xii_course_id"));
        // qualification.setElastic_time((String) after.get("elastic_time"));
        // qualification.setUpdated_on((String)after.get("updated_on"));
        // qualification.setCreated_on((String)after.get("created_on"));
        // qualification.setXii_university_id(new BigDecimal((String) after.get("xii_university_id")));
        // qualification.setXii_institution_id(new BigDecimal((String)after.get("xii_institution_id")));
        // qualification.setXii_course_year((int)after.get("xii_course_year"));
        // qualification.setXii_percentage((int)after.get("xii_percentage"));
        // qualification.setCompetitive_exam_id((int) after.get("competitive_exam_id"));
        // qualification.setX_roll_no((String) after.get("x_roll_no"));
        // qualification.setUpdated_by((String)after.get("updated_by"));
        // qualification.setX_roll_no((String) after.get("x_roll_no"));
        // qualification.setXii_roll_no((String) after.get("xii_roll_no"));
        // qualification.setCreated_by((String) after.get("created_by"));
        // qualification.setRank_holder_univesity_id((String)after.get("rank_holder_univesity_id"));
        // qualification.setClass_section((String)after.get("class_section"));
        // qualification.setApplication_id((String)after.get("application_id"));
        // qualification.setRoll_no((String)after.get("roll_no"));
        // qualification.setCompetitive_exam_rollno((String)after.get("competitive_exam_rollno"));


// scheme.setApplication_id(after.get("application_id") == null ? null : (String)after.get("application_id"));
// scheme.setScheme_id(after.get("scheme_id") == null ? 0 :Integer.parseInt((String)after.get("scheme_id")));
// scheme.setTotal_verif_level(after.get("total_verif_level") == null ? 0 : (int) after.get("total_verif_level"));
// scheme.setCurrent_verif_level(after.get("current_verif_level") == null ? 0 : (int) after.get("current_verif_level"));
// scheme.setCurrent_verif_status(after.get("current_verif_status") == null ? "" : (String) after.get("current_verif_status"));
// scheme.setVerif_remarks(after.get("verif_remarks") == null ? null : String.valueOf(after.get("verif_remarks")));
// scheme.setForm_correction_level(after.get("form_corrrection_level") == null ? 0 : (int) after.get("form_corrrection_level"));
// scheme.setIs_form_processed(after.get("is_form_processed") == null ? false : (Boolean) after.get("is_form_processed"));
// scheme.setIs_selected(after.get("is_selected") == null ? false : (Boolean) after.get("is_selected"));
// scheme.setMerit_rank(after.get("merit_rank") == null ? 0 : (int) after.get("merit_rank"));
// scheme.setMerit_list_batch_no(after.get("merit_list_batch_no") == null ? null : String.valueOf(after.get("merit_list_batch_no")));
// scheme.setPfms_reg_batch_no(after.get("pfms_reg_batch_no") == null ? null : String.valueOf(after.get("pfms_reg_batch_no")));
// scheme.setStatus(after.get("status") == null ? 0 : (int) after.get("status"));
// scheme.setUpdated_by(after.get("updated_by") == null ? null : String.valueOf(after.get("updated_by")));
// scheme.setUpdated_on(after.get("updated_on") == null ? null : String.valueOf(after.get("updated_on")));
// scheme.setCreated_by(after.get("created_by") == null ? null : String.valueOf(after.get("created_by")));
// scheme.setIs_scheme_changed(after.get("is_scheme_changed") == null ? null : (Boolean) after.get("is_scheme_changed"));
// scheme.setCreated_on(after.get("created_on") == null ? null : String.valueOf(after.get("created_on")));


   // registration.setApplication_id(after.get("application_id") == null ? null : String.valueOf(after.get("application_id")));
            // registration.setFresh_renewal(after.get("fresh_renewal") == null ? "" : (String) after.get("fresh_renewal"));
            // registration.setDomicile_state_id(after.get("domicile_state_id") == null ? "" : (String) after.get("domicile_state_id"));
            // registration.setApplicant_name(after.get("applicant_name") == null ? null : String.valueOf(after.get("applicant_name")));
            // registration.setAadhaar_no(after.get("aadhaar_no") == null ? null : String.valueOf(after.get("aadhaar_no")));
            // registration.setGender(after.get("gender") == null ? null : String.valueOf(after.get("gender")));
            // registration.setDate_of_birth(after.get("date_of_birth") == null ? null : String.valueOf(after.get("date_of_birth")));
            // registration.setCategory_id(after.get("category_id") == null ? "" : (String) after.get("category_id"));
            // registration.setIs_minority(after.get("is_minority") == null ? null : (Boolean) after.get("is_minority"));
            // registration.setDisability_flag(after.get("disability_flag") == null ? false : (Boolean) after.get("disability_flag"));
            // registration.setDisablity_type(after.get("disablity_type") == null ? 0 : (int) after.get("disablity_type"));
            // registration.setDisability_percentage(after.get("disability_percentage") == null ? 0 : (int) after.get("disability_percentage"));
            // registration.setOrphan_flag(after.get("orphan_flag") == null ? false : (Boolean) after.get("orphan_flag"));
            // registration.setHosteller(after.get("hosteller") == null ? false : (Boolean) after.get("hosteller"));
            // registration.setAnnual_family_income(after.get("annual_family_income") == null ? 0 : (int) after.get("annual_family_income"));
            // registration.setMarital_status(after.get("marital_status") == null ? 0 : (int) after.get("marital_status"));
            // registration.setParent_occupation(after.get("parent_occupation") == null ? 0 : (int) after.get("parent_occupation"));
            // registration.setFather_name(after.get("father_name") == null ? null : String.valueOf(after.get("father_name")));
            // registration.setMother_name(after.get("mother_name") == null ? null : String.valueOf(after.get("mother_name")));
            // registration.setGaurdian_name(after.get("gaurdian_name") == null ? null : String.valueOf(after.get("gaurdian_name")));
            // registration.setBank_id(after.get("bank_id") == null ? null : (BigDecimal) after.get("bank_id"));
            // registration.setBranch_ifsc_code(after.get("branch_ifsc_code") == null ? null : String.valueOf(after.get("branch_ifsc_code")));
            // registration.setBank_account_no(after.get("bank_account_no") == null ? null : String.valueOf(after.get("bank_account_no")));
            // registration.setPfms_code(after.get("pfms_code") == null ? null : String.valueOf(after.get("pfms_code")));
            // registration.setPermanent_state_id(after.get("permanent_state_id") == null ? null : (BigDecimal) after.get("permanent_state_id"));
            // registration.setPermanent_district_id(after.get("permanent_district_id") == null ? null : (BigDecimal) after.get("permanent_district_id"));
            // registration.setPermanent_mandal_id(after.get("permanent_mandal_id") == null ? null : (BigDecimal) after.get("permanent_mandal_id"));
            // registration.setPermanent_village_id(after.get("permanent_village_id") == null ? null : (BigDecimal) after.get("permanent_village_id"));
            // registration.setPermanent_address(after.get("permanent_address") == null ? null : String.valueOf(after.get("permanent_address")));
            // registration.setPermanent_pincode(after.get("permanent_pincode") == null ? null : (BigDecimal) after.get("permanent_pincode"));
            // registration.setEmail_id(after.get("email_id") == null ? null : String.valueOf(after.get("email_id")));
            // registration.setMobile_no(after.get("mobile_no") == null ? "" : (String) after.get("mobile_no"));
            // registration.setRegistered_date(after.get("registered_date") == null ? null : String.valueOf(after.get("registered_date")));
            // registration.setApplication_level(after.get("application_level") == null ? null : (BigDecimal) after.get("application_level"));
            // registration.setUpdated_by(after.get("updated_by") == null ? null : String.valueOf(after.get("updated_by")));
            // registration.setUpdated_on(after.get("updated_on") == null ? null : String.valueOf(after.get("updated_on")));
            // registration.setCreated_by(after.get("created_by") == null ? null : String.valueOf(after.get("created_by")));
            // //  registration.setCreated_on(after.get("created_on") == null ? (long) 0 : (Long) after.get("created_on"));
            // registration.setReligion(after.get("religion") == null ? 0 : (int) after.get("religion"));
            // registration.setAccount_holder_type(after.get("account_holder_type") == null ? "" : (String) after.get("account_holder_type"));
            // registration.setAdmission_fee(after.get("admission_fee") == null ? null : (BigDecimal) after.get("admission_fee"));
            // registration.setTution_fee(after.get("tution_fee") == null ? null : (BigDecimal) after.get("tution_fee"));
            // registration.setClass_start_date(after.get("class_start_date") == null ? null : String.valueOf(after.get("class_start_date")));
            // registration.setHash_dob(after.get("hash_dob") == null ? null : String.valueOf(after.get("hash_dob")));
            // registration.setPre_post_matric(after.get("pre_post_matric") == null ? 0 : (int) after.get("pre_post_matric"));
            // registration.setInst_verify_by(after.get("inst_verify_by") == null ? null : (BigDecimal) after.get("inst_verify_by"));
            // registration.setInst_verify_date(after.get("inst_verify_date") == null ? null : String.valueOf(after.get("inst_verify_date")));
            // registration.setInst_verify(after.get("inst_verify") == null ? null : String.valueOf(after.get("inst_verify")));
            // registration.setModeofstudy(after.get("modeofstudy") == null ? 0 : (int) after.get("modeofstudy"));
            // registration.setIp_address(after.get("ip_address") == null ? null : String.valueOf(after.get("ip_address")));
            // registration.setApplication_status(after.get("application_status") == null ? null : String.valueOf(after.get("application_status")));
            // registration.setMisc_fee(after.get("misc_fee") == null ? 0 : (int) after.get("misc_fee"));
            // registration.setDist_verify_by(after.get("dist_verify_by") == null ? null : (BigDecimal) after.get("dist_verify_by"));
            // registration.setDist_verify_date(after.get("dist_verify_date") == null ? null : String.valueOf(after.get("dist_verify_date")));
            // registration.setDist_verify(after.get("dist_verify") == null ? null : String.valueOf(after.get("dist_verify")));
            // registration.setState_verify_by(after.get("state_verify_by") == null ? null : (BigDecimal) after.get("state_verify_by"));
            // registration.setState_verify_date(after.get("state_verify_date") == null ? null : String.valueOf(after.get("state_verify_date")));
            // registration.setState_verify(after.get("state_verify") == null ? null : String.valueOf(after.get("state_verify")));
            // registration.setDist_document_verify(after.get("dist_document_verify") == null ? null : String.valueOf(after.get("dist_document_verify")));
            // registration.setInst_document_verify(after.get("inst_document_verify") == null ? null : String.valueOf(after.get("inst_document_verify")));
            // registration.setMin_verify_by(after.get("min_verify_by") == null ? null : (BigDecimal) after.get("min_verify_by"));
            // registration.setMin_verify(after.get("min_verify") == null ? null : String.valueOf(after.get("min_verify")));
            // registration.setMin_verify_date(after.get("min_verify_date") == null ? null : String.valueOf(after.get("min_verify_date")));
            // registration.setDelete_record(after.get("delete_record") == null ? null : String.valueOf(after.get("delete_record")));
            // registration.setDeleted_by(after.get("deleted_by") == null ? 0 : (int) after.get("deleted_by"));
            // registration.setDelete_on(after.get("delete_on") == null ? null : String.valueOf(after.get("delete_on")));
            // registration.setDelete_ip_address(after.get("delete_ip_address") == null ? null : String.valueOf(after.get("delete_ip_address")));
            // registration.setDeleted_by_level(after.get("deleted_by_level") == null ? 0 : (int) after.get("deleted_by_level"));
            // registration.setAgency_id(after.get("agency_id") == null ? null : String.valueOf(after.get("agency_id")));
            // registration.setAgency_name(after.get("agency_name") == null ? null : String.valueOf(after.get("agency_name")));
            // registration.setRank(after.get("rank") == null ? null : new BigInteger(String.valueOf(after.get("rank"))));
            // registration.setOutofquota(after.get("outofquota") == null ? null : (Integer) after.get("outofquota"));
            // registration.setIs_selected(after.get("is_selected") == null ? false : (Boolean) after.get("is_selected"));
            // registration.setLot_number(after.get("lot_number") == null ? null : new BigInteger(String.valueOf(after.get("lot_number"))));
            // registration.setIs_approved_for_payment(after.get("is_approved_for_payment") == null ? "" : (String) after.get("is_approved_for_payment"));
            // registration.setApproved_by(after.get("approved_by") == null ? null : String.valueOf(after.get("approved_by")));
            // registration.setApproved_by_ip(after.get("approved_by_ip") == null ? null : String.valueOf(after.get("approved_by_ip")));
            // registration.setLot_number_history(after.get("lot_number_history") == null ? null : String.valueOf(after.get("lot_number_history")));
            // registration.setSanity(after.get("sanity") == null ? null : String.valueOf(after.get("sanity")));
            // registration.setEntitled_fee_amount(after.get("entitled_fee_amount") == null ? null : (BigDecimal) after.get("entitled_fee_amount"));
            // registration.setEntitled_lumpsump_amount(after.get("entitled_lumpsump_amount") == null ? null : (BigDecimal) after.get("entitled_lumpsump_amount"));
            // registration.setScheme(after.get("scheme") == null ? 0 : (int) after.get("scheme"));
            // registration.setPayment_stoped(after.get("payment_stoped") == null ? null : String.valueOf(after.get("payment_stoped")));
            // registration.setPayment_sent(after.get("payment_sent") == null ? "" : (String) after.get("payment_sent"));
            // registration.setBank_name(after.get("bank_name") == null ? null : String.valueOf(after.get("bank_name")));
            // registration.setR_pfms_payment_status(after.get("r_pfms_payment_status") == null ? null : String.valueOf(after.get("r_pfms_payment_status")));
            // registration.setBenif_status(after.get("benif_status") == null ? null : String.valueOf(after.get("benif_status")));
            // registration.setBenif_error_code(after.get("benif_error_code") == null ? "" : (String) after.get("benif_error_code"));
            // registration.setBenif_rej_reason_code(after.get("benif_rej_reason_code") == null ? null : String.valueOf(after.get("benif_rej_reason_code")));
            // registration.setBenif_rej_reson_desc(after.get("benif_rej_reson_desc") == null ? null : String.valueOf(after.get("benif_rej_reson_desc")));
            // registration.setPay_method(after.get("pay_method") == null ? "": (String) after.get("pay_method"));
            // registration.setPay_amt_centre_shr(after.get("pay_amt_centre_shr") == null ? null : (BigDecimal) after.get("pay_amt_centre_shr"));
            // registration.setPay_amt_state_shr(after.get("pay_amt_state_shr") == null ? null : (BigDecimal) after.get("pay_amt_state_shr"));
            // registration.setPayment_transaction_id(after.get("payment_transaction_id") == null ? null : String.valueOf(after.get("payment_transaction_id")));
            // registration.setIn_rank(after.get("in_rank") == null ? false : (Boolean) after.get("in_rank"));
            // registration.setPayment_status(after.get("payment_status") == null ? "" : (String) after.get("payment_status"));
            // registration.setPayment_error_code(after.get("payment_error_code") == null ? null : String.valueOf(after.get("payment_error_code")));
            // registration.setPfms_ben_code(after.get("pfms_ben_code") == null ? null : String.valueOf(after.get("pfms_ben_code")));
            // registration.setPayment_rej_reason_code(after.get("payment_rej_reason_code") == null ? null : String.valueOf(after.get("payment_rej_reason_code")));
            // registration.setPayment_rej_reason_desc(after.get("payment_rej_reason_desc") == null ? null : String.valueOf(after.get("payment_rej_reason_desc")));
            // registration.setAutomate_benificiary(after.get("automate_benificiary") == null ? 0 : (int) after.get("automate_benificiary"));
            // registration.setAutomate_payment(after.get("automate_payment") == null ? 0 : (int) after.get("automate_payment"));
            // registration.setIs_fee_calculated(after.get("is_fee_calculated") == null ? false : (Boolean) after.get("is_fee_calculated"));
            // registration.setPayment_batch_id(after.get("payment_batch_id") == null ? null : String.valueOf(after.get("payment_batch_id")));
            // registration.setIs_singlegirlchild(after.get("is_singlegirlchild") == null ? false : (Boolean) after.get("is_singlegirlchild"));
            // registration.setIs_university_rankholder(after.get("is_university_rankholder") == null ? false : (Boolean) after.get("is_university_rankholder"));
            // registration.setEnc_aadhaar(after.get("enc_aadhaar") == null ? null : String.valueOf(after.get("enc_aadhaar")));
            // registration.setAadhaar_virtual_id(after.get("aadhaar_virtual_id") == null ? null : String.valueOf(after.get("aadhaar_virtual_id")));
            // registration.setAadhaar_registration_mode(after.get("aadhaar_registration_mode") == null ? null : String.valueOf(after.get("aadhaar_registration_mode")));
            // registration.setAadhaar_token_no(after.get("aadhaar_token_no") == null ? null : String.valueOf(after.get("aadhaar_token_no")));
            // registration.setIs_xml_generated_for_payment(after.get("is_xml_generated_for_payment") == null ? false : (Boolean) after.get("is_xml_generated_for_payment"));
            // registration.setIs_payment_file_pushed(after.get("is_payment_file_pushed") == null ? false : (Boolean) after.get("is_payment_file_pushed"));
            // registration.setIs_response_recieved(after.get("is_response_received") == null ? false : (Boolean) after.get("is_response_recieved"));
            // registration.setScholarship_incentive(after.get("scholarship_incentive") == null ? null : String.valueOf(after.get("scholarship_incentive")));
            // registration.setSupplementary(after.get("supplementary") == null ? false : (Boolean) after.get("supplementary"));
            // registration.setRed_flag(after.get("red_flag") == null ? null : String.valueOf(after.get("red_flag")));
            // registration.setRation_card_no(after.get("ration_card_no") == null ? null : String.valueOf(after.get("ration_card_no")));
            // registration.setRation_card_member_no(after.get("ration_card_member_no") == null ? null : String.valueOf(after.get("ration_card_member_no")));
            // registration.setName_as_in_ration_card(after.get("name_as_in_ration_card") == null ? null : String.valueOf(after.get("name_as_in_ration_card")));
            // registration.setAadhaar_vault_ref_token(after.get("aadhaar_vault_ref_token") == null ? null : String.valueOf(after.get("aadhaar_vault_ref_token")));
            // registration.setAdmission_no(after.get("admission_no") == null ? null : String.valueOf(after.get("admission_no")));
            // registration.setAdmission_year(after.get("admission_year") == null ? 0 : (int) after.get("admission_year"));
            // registration.setElastic_time(after.get("elastic_time") == null ? null : String.valueOf(after.get("elastic_time")));
            // registration.setParents_not_alive(after.get("parents_not_alive") == null ? 0 : (int) after.get("parents_not_alive"));


    // @GET
    // @Produces(MediaType.TEXT_PLAIN)
//     public String hello( @QueryParam("application_id") String application_id) throws IOException {

//         query1 = query1.replace("@{application_id}",application_id);

//         query2 = query2.replace("@{application_id}",application_id);

//         query3 = query3.replace("@{application_id}",application_id);

//         query5 = query5.replace("@{application_id}",application_id);

// //         System.out.println("Hello  Jai"+ query2);
// //         // String query4 = "select daf.application_id, unia.university_name, unib.university_name, unic.university_name, unid.university_name, insta.institution_name ,instb.institution_name, " + 
// //         // "coursea.course_name, coursec.course_name, coursec.duration, coursed.course_name, "+
// //         // "instc.institution_id, instc.institution_name, instc.district_id, instc.university_id, instc.pin_code, instc.state_id, instc.email_id, "+
// //         // "instc.delete_flag, instc.phone_no, instc.contact_person_name, instc.contact_person_mobile, instc.contact_person_phone, "+
// //         // "instc.contact_person_email, instc.postal_address_department, instc.postal_address_street, "+
// //         // "instc.postal_address_building, instc.inst_address_line1, instc.inst_address_line2, instc.inst_address_town_code, "+
// //         // "instc.inst_address_town_name, instc.final_aishe_code, instc.is_contact_updated, instc.is_approved_district, "+
// //         // "instc.is_submit_form, instc.inst_head_name, instc.inst_head_mobile, instc.inst_head_phone, instc.inst_head_email, "+
// //         // "instc.inst_head_designation, instc.inst_form_reference_no, instc.is_save_form, distp.district_name, "+
// //         // "st.state_name, stc.state_name, comp.examination_name "+
// //         // "from data_applicant_qualifications as daf "+
// //         // "left join mst_university unia on  daf.c_university_id = unia.university_id "+ 
// //         // "left join mst_university unib on  daf.p_university_id = unib.university_id "+
// //         // "left join mst_university unic on  daf.x_university_id = unic.university_id "+
// //         // "left join mst_university unid on  daf.xii_university_id = unid.university_id "+
// //         // "left join mst_institution insta on  daf.x_institution_id = insta.institution_id "+
// //         // "left join mst_institution instb on  daf.xii_institution_id = instb.institution_id "+
// //         // "left join mst_institution instc on  daf.c_institution_id = instb.institution_id "+
// //         // "left join mst_course coursea on  daf.xii_course_id = coursea.course_id "+
// //         // "left join mst_course coursec on  daf.c_course_id = coursec.course_id "+
// //         // "left join mst_course coursed on  daf.p_course_id = coursed.course_id "+
// //         // "left join mst_districts distp on  daf.previous_district_id = distp.district_id "+
// //         // "left join mst_states st on  daf.previous_inst_state_id = st.state_id "+
// //         // "left join mst_states stc on  daf.current_inst_state_id = stc.state_id "+
// //         // "left join mst_competitive_exams comp on  daf.competitive_exam_id = mst_competititve_exams.exam_id;";

// // // String query5 = "select application_id, aadhaar_verif_status from data_applicant_agency_verifications where application_id='1ABC'";

// //         // String query3 = "select daw.application_id, daw.rank, ftype.force_type_desc, wcat.category_name, mrank.rank_desc, railrank.rank_name, railzone.zone_id "+
// //         // "from data_applicant_registration_warb as daw " +
// //         // "left join mst_force_type as ftype on ftype.force_type = daw.force_type_id "+
// //         // "left join mst_warb_category as wcat on wcat.category_id = daw.category_id " +
// //         // "left join mst_rank as mrank on mrank.force_type = daw.force_type_id " + 
// //         // "left join mst_railway_rank as railrank on railrank.rank_id = daw.railway_rank " +
// //         // "left join mst_railway_zone as railzone on railzone.zone_id = daw.zone where application_id='1ABC' ";

// //         // String query2 = "select application_id, sch.scheme_id, total_verif_level, current_verif_level, current_verif_status, verif_remarks, form_corrrection_level, is_form_processed, is_selected, merit_rank, "+
// //         // "sch.scheme_name, min.ministry_id, ministry_short_name,ministry_name,parent_ministry,delete_flag,display_order,lock,is_ministry_link_created, is_active,state_central, scheme_type, from mst_schemes as sch inner join data_applicant_applied_schemes as daas on daas.scheme_id = sch.scheme_id inner join mst_ministries as min on min.ministry_id = sch.ministry_id where application_id='1ABC';";
        
    


// // //         String query1 = "select application_id, fresh_renewal, domicile_state_id, applicant_name, aadhaar_no, gender, date_of_birth, dar.category_id, is_minority, disability_flag, dar.disablity_type, disability_percentage, orphan_flag, hosteller, annual_family_income, marital_status, parent_occupation, father_name, mother_name, gaurdian_name, bank_id, branch_ifsc_code, bank_account_no, pfms_code, permanent_state_id, permanent_district_id, permanent_mandal_id, permanent_village_id, permanent_address, permanent_pincode, email_id, mobile_no, registered_date, dar.application_level, updated_by, updated_on, created_by, created_on, religion, account_holder_type, admission_fee, tution_fee, class_start_date, hash_dob, pre_post_matric, inst_verify_by, inst_verify_date, inst_verify, modeofstudy, ip_address, dar.application_status, misc_fee, dist_verify_by, dist_verify_date, dist_verify, state_verify_by, state_verify_date, state_verify, dist_document_verify, inst_document_verify, min_verify_by, min_verify, min_verify_date, delete_record, deleted_by, delete_on, delete_ip_address, deleted_by_level, agency_id, agency_name, rank, outofquota, is_selected, lot_number, is_approved_for_payment, approved_by, approved_by_ip, lot_number_history, sanity, entitled_fee_amount, entitled_lumpsump_amount, scheme, payment_stoped, payment_sent, bank_name, r_pfms_payment_status, benif_status, benif_error_code, benif_rej_reason_code, benif_rej_reson_desc, pay_method, pay_amt_centre_shr, pay_amt_state_shr, payment_transaction_id, in_rank, payment_status, payment_error_code, pfms_ben_code, payment_rej_reason_code, payment_rej_reason_desc, automate_benificiary, automate_payment, is_fee_calculated, payment_batch_id, is_singlegirlchild, is_university_rankholder, enc_aadhaar, aadhaar_virtual_id, aadhaar_registration_mode, aadhaar_token_no, is_xml_generated_for_payment, is_payment_file_pushed, is_response_recieved, scholarship_incentive, supplementary, red_flag, ration_card_no, ration_card_member_no, name_as_in_ration_card, aadhaar_vault_ref_token, admission_no, admission_year, elastic_time, parents_not_alive, "+
// // //         "pfms_state_code,state_name,short_name,state_ut, disability_desc, district_name, dist.district_id, religion_name, marital_status_name, "+
// // // "gender_name, apst.description, regcat.category_name, course_type, occup_name "+
// // // "from data_applicant_registration_details as dar "+
// // // "left join mst_states as st on st.state_id = dar.domicile_state_id "+
// // // "left join mst_districts as dist on dist.district_id = dar.permanent_district_id "+
// // // "left join mst_disability_type as dis on dis.disability_type = dar.disablity_type "+
// // // "left join mst_religion as rel on rel.religion_id = dar.religion "+
// // // "left join mst_marital_status as mrtl on mrtl.marital_id = dar.marital_status "+
// // // "left join mst_gender as gen on gen.gender_id = dar.gender " +
// // // "left join mst_application_status as apst on apst.application_status = dar.application_status "+
// // // "left join mst_scholarship_category as scat on scat.category_id = dar.pre_post_matric "+
// // // "left join mst_course_type as ctyp on ctyp.course_type_id=dar.modeofstudy "+
// // // "left join mst_parental_occupation as pocc on pocc.occup_id = dar.parent_occupation "+
// // // "left join mst_category as regcat on regcat.category_id = dar.category_id where application_id='1ABC'; ";

// // // String query= "select approved_date,    FROM data_applicant_registration_details;";

// // String query = "select * from dummytable;";

//         List<Object> object = entityManager.createNativeQuery(query1).getResultList();
//         ObjectMapper mapper = new ObjectMapper();
//         DataApplicationRegistration std= new DataApplicationRegistration();
//         for (Object obj : object) {
//             if (obj.getClass().isArray()) {
//                 List<Object> list = Arrays.asList((Object[]) obj);
//                 System.out.println(list);
//                 std = new DataApplicationRegistration(list);
//                 System.out.println(list.toString()+"hello world");
//             }
//         }

//         List<Object> object1 = entityManager.createNativeQuery(query2).getResultList();

//         DataApplicantAppliedScheme std1= new DataApplicantAppliedScheme();
//         for (Object obj : object1) {
//             if (obj.getClass().isArray()) {
//                 List<Object> list = Arrays.asList((Object[]) obj);
//                 System.out.println(list);
//                 std1 = new DataApplicantAppliedScheme(list);
//                 System.out.println(list.toString()+"hello world");
//             }
//         }

//         List<Object> object3 = entityManager.createNativeQuery(query3).getResultList();

//         DataApplicantregistrationwarb std3= new DataApplicantregistrationwarb();
//         for (Object obj : object3) {
//             if (obj.getClass().isArray()) {
//                 List<Object> list = Arrays.asList((Object[]) obj);
//                 System.out.println(list + "hi I am here");
//                 // std3 = new DataApplicantregistrationwarb(list);
//                 // System.out.println(list.toString()+"hello quer3");
//             }
//         }

//         // List<Object> object5 = entityManager.createNativeQuery(query5).getResultList();

//         // Dataapplicantagencyverification std5= new Dataapplicantagencyverification();
//         // for (Object obj : object5) {
//         //     if (obj.getClass().isArray()) {
//         //         List<Object> list = Arrays.asList((Object[]) obj);
//         //         System.out.println(list +"query5");
//         //         std5 = new Dataapplicantagencyverification(list);
//         //         System.out.println(list.toString()+"hello world555");
//         //     }
//         // }




        
//         // List<Object> object4 = entityManager.createNativeQuery(query4).getResultList();

//         // StudentQualification2122 std4= new StudentQualification2122();
//         // for (Object obj : object3) {
//         //     if (obj.getClass().isArray()) {
//         //         List<Object> list = Arrays.asList((Object[]) obj);
//         //         System.out.println(list);
//         //         std4 = new StudentQualification2122(list);
//         //         System.out.println(list.toString()+"hello world");
//         //     }
//         // }

//         pojo newpojo= new pojo(std,std1,std3);
//         // newpojo.setReg(std);
//         // newpojo.setScheme(std1);
//         // newpojo.setWarb(std3);
//         // newpojo.setVerify(std5);

//         Request request = new Request(
//             "PUT",
//             "/student/_doc/" + 102); 
//     request.setJsonEntity(JsonObject.mapFrom(newpojo).toString()); 
//     restClient.performRequest(request);

//         try {
//            String test =  mapper.writeValueAsString(newpojo);
//            System.out.println(test);
//            return test;
//         } catch (JsonProcessingException e) {
//             // TODO Auto-generated catch block
//             e.printStackTrace();
//         }


//         return null;
    
}


