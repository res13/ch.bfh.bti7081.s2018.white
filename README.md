# Software Engineering and Design

## Group: ch.bfh.bti7081.s2018.white
* Andreas Erb (res13)
* Beat Schärz (pidu2)
* Nik Arm (projektins)
* Dario Furigo (VirtSyntaxError)
* Ivo Kozina (kozinai)
* Jannick Lüdi (janexander)
* Ohran Mujkic (mujko1)

## Useful links
* [Diary](https://github.com/res13/ch.bfh.bti7081.s2018.white/blob/master/doc/diary.md)

## Project Topic
* target user: close relative of a patient
* disease: social anxiety disorder

## Project Specifications
A regional health authority wishes to procure an patient management system
(PMS) to manage the care of patients suffering from mental health problems. The
overall goals of the system are:

1. To provide medical staff (doctors and health visitors) with timely information
to facilitate the treatment of patients.
2. To support patients and their relatives in coping with the desease.

Most mental health patients do not require dedicated hospital treatment but need
to attend specialist clinics regularly where they can meet a doctor who has
detailed knowledge of their problems. The health authority has a number of
clinics that patients may attend. To make it easier for patients to attend, these
clinics are not just run in hospitals. They may also be held in local medical
practices or community centres. Patients need not always attend the same clinic
and some clinics may support ‘drop in’ as well as pre-arranged appointments.
The nature of mental health problems may be that patients are often
disorganised so may miss appointments, deliberately or accidentally lose
prescriptions and medication, forget instructions or make unreasonable demands
on medical staff. In a minority of cases, they may be a danger to themselves or to
other people. They may regularly change address and may be homeless on a
long-term or short-term basis. Where patients are dangerous, they may need to
be ‘sectioned’ – confined to a secure hospital for treatment and observation.

Users of the system include clinical staff (doctors, nurses, health visitors),
receptionists who make appointments and medical records staff. Reports are
generated for hospital management by medical records staff. Management have
no direct access to the system.
The system is affected by two pieces of legislation

1. Data Protection Act that governs the confidentiality of personal information
2. Mental Health Act that governs the compulsory detention of patients deemed
to be a danger to themselves or others.

The system is NOT a complete medical records system where all information
about a patients’ medical treatment is maintained. It is solely intended to support
mental health care so if a patient is suffering from some other unrelated
condition (such as high blood pressure) this would not be formally recorded in
the system.