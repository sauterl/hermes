# hermes

_hermes_, the [herald](https://en.wikipedia.org/wiki/Hermes) of the Olympians, is a small CLI utility to send batched
emails with some customised content. **Note**: _hermes_ relies on external infrastructure
such as a SMTP server.

**Main user story:**

Someone managing a number of groups with several people per group and these
groups need to get information via email. Some of the email content is similar for all groups,
some content specific to groups. Furthermore, another set of people should get cc-ed, depending on the group.

To solve this, one could write an email template, carefully copy-paste the groups' emails from
a table and then, add the CC recipients where necessary. Before sending, the template content
would be manually replaced by the specific content. An endeavour too long to really take.


_hermes_ for the win! With _hermes_, the above described scenario is nothing more than providing
a few tables, a template and the call of the _hermes_ command and the emails are sent.

## Prerequisites

* _hermes_ is written in [Kotlin](https://kotlinlang.org) and thus requires a [JVM](https://openjdk.java.net/install/)
* Third party infrastructure for actually sending the email is required. For instance Gmail or other Email providers

## Usage

Since _hermes_ is for writing batched, templated emails, the following components
have to be provided:

 * A template for the email
 * A dictionary with template replacements
 * An address book with recipients
 * Credentials to the external SMTP server

See below for further details about the configuration.

Sending an email then is as simply as issuing the command:

```
$> java -jar hermes.jar batch \
--mail=email-template.txt \
--credentials=credentials.json \
--dictionary=dictionary.csv \
--tasks=tasks.csv \
--addresses=addresses.csv
```

Read below for an example of each of the file formats.
See [credentials](#Credentials) for info about the credentials.

### Example Email Template

The example email template, `email-template.txt` is a plain text file:

```
To:$key
From:alice@mail.com
Subject:Welcome from hermes, $addressee

Hi there, $addressee
This is a batched, email sent from hermes.

Your specific information is: $info

Don't forget that you are respondible for $target

Best,
Alice
```

Note the format, as described [below](#Email). The `to` line is required, however in batch mode,
the key there is ignored (so technically, you could use any non whitespace character as replacement for `$key`)

### Example Dictionary

The example dictionary, `dictionary.csv` is a table as follows.

The header contains all the placeholders from the template above (using the dollar `$` prefix is just a convention).
Be aware that the `key` column must match with the one from the _tasks_ file!

```csv
key,$addressee,$info,$target
g1,Group 01,"https://example.com/group01,food
g2,Group 02,"https://example.com/group02,"beverages & drinks"
g3,"Olympian Dieties","https://example.com/admin,"Being a diety"
```

### Example Address Book

The example address book, `addresses.csv` is a table with email addresses-key mappings:

```csv
key,email
group1,bob@mail.com
group1,john@mail.com
group1,jane@mail.com
group2,turing@maill.com
group2,dijkstra@mail.com
group2,lee@mail.com
dieties,zeus@olympian-mail.gr
dieties,poseidon@olympian-mail.gr
dieties,hades@olympian-mail.gr
```

Note that there is one entry per line, to form a group of recipients, group them by the same key.
This key is then used in the `to` column of the _tasks_ file!

### Example Tasks

The example task file, `tasks.csv` is a table with (dicitonary-)key and (addressbook-)key pairs:

```csv
key,to,cc
g1,group1,zeus@olympian-mail.gr
g2,group2,poseidon@olympian-mail.gr
g3,dieties,kronos@titans.gr
```

Note, the `cc` and `bcc` columns are optional

## Format

The major components of _hermes_ have to be configured as follows

### Email

The basic format is a line-based, colon (`:`) separated text file.
Of which, `Subject`, `To`, `From` and the message body are required.
The headers and the message body are separated by an empty line:

```text
Subject:
To:
From:
Cc:
Bcc:

Message body in plaintext
```

The `Subject` and message body might contain dictionary keys to be replaced before sending.
The recipients fields might contain keys for an address book.

### Dictionary

The dictionary is a CSV file

### Address Book

The address book is a table with a key-email address format:

```
key,email
"key0","email1"
"key1","email2"
"key2","email3"
"key3","email4"
"key4","email5"
```

**Example**

```
key,email
"GroupAlice","alice@mail.com"
"GroupAlice","bob@mail.com"
"GroupTuring","alan@turing.com"
```

### Credentials

You require a third-party SMTP setup and provide the credentials in the following format:

```json
{
  "host": "<your-smtp-host>",
  "port": 465,
  "username": "<your-username>",
  "password": "<your-password>",
  "ssl": true
}
```

Example with GMAIL:

```json
{
  "host": "smtp.googlemail.com",
  "port": 465,
  "username": "alice@gmail.com",
  "password": "alicespassword",
  "ssl": true
}
```

