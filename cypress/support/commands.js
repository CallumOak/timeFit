// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
// Cypress.Commands.add('login', (email, password) => { ... })
//
//
// -- This is a child command --
// Cypress.Commands.add('drag', { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add('dismiss', { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite('visit', (originalFn, url, options) => { ... })
Cypress.Commands.add('loginHTTP', (username, password) => {
    const optionsLogin = {
        method : 'POST',
        url : 'http://localhost:8080/api/auth/login',
        body : {
            "username": username,
            "password": password
        },
        form : true,
        followRedirect: true
    }
    cy.request(optionsLogin).then((response) =>{
        sessionStorage.setItem("lh-cred", response.body['access_token'])
    })
})

Cypress.Commands.add('setupdb', () => {
    const optionsLogin = {
        method : 'GET',
        url : 'http://localhost:8080/api/test/setupdb'
    }
    cy.request(optionsLogin)
})