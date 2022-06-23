describe('application navigation tests', () => {
    beforeEach(() => {
        cy.setupdb()
    })
    it('login', () => {
        cy.visit('localhost:80')
        cy.get('#login').click()
        cy.get('#username').click().type('Callum').should('have.value', 'Callum')
        cy.get('#password').click().type('Callum123')
        cy.get('#loginButton').click()
        cy.url().should('include', 'calendar')
    })
})