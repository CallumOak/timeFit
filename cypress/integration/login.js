describe("user management tests", function(){
    it("logging in", function(){
        cy.visit('localhost:4200')
        cy.contains('Login').click()
        cy.get('input[type="text"]').type('John').should('have.value', 'John')
        cy.get('input[type="password"]').type('John123')
        cy.get('.btn-primary').click()
        cy.contains('Logged in as ROLE_USER.').should('exist')
        cy.contains('Calendar').click()
    })
})