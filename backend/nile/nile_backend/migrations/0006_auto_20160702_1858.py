# -*- coding: utf-8 -*-
# Generated by Django 1.9.6 on 2016-07-02 18:58
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('nile_backend', '0005_user_token'),
    ]

    operations = [
        migrations.AddField(
            model_name='package',
            name='mins_until_delivery',
            field=models.FloatField(default=None, null=True),
        ),
        migrations.AddField(
            model_name='package',
            name='sender',
            field=models.CharField(default=None, max_length=255, null=True),
        ),
    ]
