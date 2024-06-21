'use strict';

let form = document.forms["create"];
createNewUser()

function createNewUser() {
    form.addEventListener("submit", ev => {
        ev.preventDefault();
        let roles = [];
        for (let i = 0; i < form.role.options.length; i++) {
            if (form.role.options[i].selected) roles.push({
                id: form.role.options[i].value,
                roleName: "ROLE_" + form.role.options[i].text
            });
        }
        fetch("api/admin", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                userName: form.userName.value,
                name: form.name.value,
                surname: form.surname.value,
                email: form.email.value,
                yearOfBirth: form.yearOfBirth.value,
                password: form.password.value,
                role: roles
            })
        }).then(() => {
            form.reset();
            $('#home-tab').click();
            getTableUser();
        });
    });
}