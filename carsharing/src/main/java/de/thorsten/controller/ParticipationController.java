/*
 * noch nicht benötigt - erst wenn Teilnahme aus UI erstellt wird.
 */
package de.thorsten.controller;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import de.thorsten.model.Participation;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://sfwk.org/Documentation/WhatIsThePurposeOfTheModelAnnotation
@Model
public class ParticipationController {

    @Inject
    private FacesContext facesContext;


    @Produces
    @Named
    private Participation newParticipation;

}
