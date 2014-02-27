/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.thorsten.data;

import de.thorsten.model.MailingList;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class MailingListListProducer {

    @Inject
    private MailingListRepository mailingListRepository;

    private List<MailingList> mailingLists;
    
    private Collection<String> allAvailableEMailAdresses;
    
    @Inject
    Logger log;

    // @Named provides access the return value via the EL variable name "mailingLists" in the UI (e.g.
    // Facelets or JSP view)

   
    @Produces
    @Named
    public List<MailingList> getMailingLists() {
        return mailingLists;
    }


    @PostConstruct
    public void loadStaticData() {
        mailingLists = mailingListRepository.findAll();
        try {
            mailingListRepository.findAllAvailableEMailAdresses();
            if (mailingListRepository.findAllAvailableEMailAdresses().size() == 1) {
                   setAllAvailableEMailAdresses(((MailingList)mailingListRepository.findAllAvailableEMailAdresses().get(0)).geteMailAdresses());
                   log.info(allAvailableEMailAdresses.size() + " eMailAdresses available");
            } else {
                log.warning("Couldn't load all available eMail Adresses - no unique identifier!");
            }
 
        } catch (Exception e) {
            log.warning("Couldn't load all available eMail Adresses");
            e.printStackTrace();
        }
    }

    /**
     * @return the allAvailableEMailAdresses
     */
    public Collection<String> getAllAvailableEMailAdresses() {
        return allAvailableEMailAdresses;
    }

    /**
     * @param allAvailableEMailAdresses the allAvailableEMailAdresses to set
     */
    public void setAllAvailableEMailAdresses(Collection<String> allAvailableEMailAdresses) {
        this.allAvailableEMailAdresses = allAvailableEMailAdresses;
    }
}
