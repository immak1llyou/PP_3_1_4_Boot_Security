async function getOneUser(id) {
    let response = await fetch("api/admin/" + id);
    return await response.json();
}

async function openAndFillInTheModal(form, modal, id) {
    modal.show();
    let user = await getOneUser(id);
    form.id.value = user.id;
    form.username.value = user.userName;
    form.name.value = user.name;
    form.surname.value = user.surname;
    form.email.value = user.email;
    form.yearOfBirth.value = user.yearOfBirth;
    form.roles.value = user.role.join(',');
}