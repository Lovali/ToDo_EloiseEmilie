# ToDo_EloiseEmilie

Ce projet a été réalisé par Eloïse Bonnet et Emilie Shih dans le cadre de notre cours de développement mobile.
Le projet consistait en la réalisation d'une application mobile permettant d'accéder à la liste de tâches d'un utilisateur de todoist.com.
L'application est reliée à la clé API du compte d'Eloïse Bonnet.

## Description générale

L'application s'ouvre sur une liste de tâches provenant du compte todoist d'Eloïse Bonnet. L'utilisateur peut supprimer des tâches, modifier leur nom, leur description et leur id, et il peut également ajouter de nouvelles tâches grâce au bouton en bas à droite de l'écran.
L'utilisateur peut également accéder aux informations du profil en cliquant sur l'image située en haut à droite. Il peut ensuite remplacer l'image du profil par une image provenant de son appareil ou en prenant directement une photographie. Il peut aussi modifier le nom et l'adresse e-mail du profil.

## Bugs repérés et non corrigés

Les bugs suivants ont été repérés durant le développement et n'ont pas réussi à être corrigés :
- Le nom et l'e-mail ne sont pas modifiés. La requête SYNC API renvoie bien un code 200 mais le nom et l'adresse mail ne sont pas modifiés pour une raison que nous ignorons.
- L'option de prendre une photo avec son téléphone pour remplacer la photo de l'utilisateur ne fonctionne pas systématiquement. En effet, il arrive parfois que la photo soit trop lourde pour que la requête puisse fonctionner.
- Lorsque l'on édite ou supprime une tâche, puis qu'on crée une tâche ensuite, la liste de tâches ne s'actualise pas pour une raison que nous ignorons.